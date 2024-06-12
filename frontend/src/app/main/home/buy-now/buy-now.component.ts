import { DatePipe } from '@angular/common';
import { Component, Input, OnInit, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { AuthenticationService } from 'app/auth/service';
import { OrderService } from 'app/main/order/order.service';
import { Product } from 'app/models/product';
import Stepper from 'bs-stepper';
import { FlatpickrOptions } from 'ng2-flatpickr';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-buy-now',
  templateUrl: './buy-now.component.html',
  styleUrls: ['./buy-now.component.scss'],
  encapsulation: ViewEncapsulation.None,
  host: { class: "ecommerce-application" },
})
export class BuyNowComponent implements OnInit {
  @Input('product') product: Product
  @Input("modal") public modal: NgbActiveModal;
  public form: FormGroup
  public submitted = false
  private checkoutStepper: Stepper;
  public flatpickrConfig: FlatpickrOptions;
  public payment = "Mã QR";
  public diffDays = 0
  public productOrder = {
    productId: 0,
    quantity: 1
  }

  get f() {
    return this.form.controls;
  }


  constructor(private _authService: AuthenticationService, private _toastrService: ToastrService, private _orderService: OrderService, private _router: Router, private _fb: FormBuilder) { }

  ngOnInit(): void {
    this.productOrder.productId = this.product.id
    const date = new Date();
    date.setDate(date.getDate() + 1);


    this.flatpickrConfig = {
      altFormat: "d-m-Y",
      altInput: true,
      minDate: date,
      onChange: (event) => {
        const pipe = new DatePipe("en-US");
        this.form.patchValue({
          giveBackDay: pipe.transform(event[0], "dd-MM-yyyy"),
        });
      },
    };


    const currentUser = this._authService.currentUserValue
    this.form = this._fb.group({
      note: null,
      receiverName: [currentUser.fullName, Validators.required],
      receiverPhone: [currentUser.phoneNumber, Validators.required],
      receiverAddress: [currentUser.address, Validators.required],
      giveBackDay: [null, Validators.required],
    });

    this.checkoutStepper = new Stepper(
      document.querySelector("#checkoutStepper"),
      {
        linear: false,
        animation: true,
      }
    );
  }

  updateQuantity(offset: number) {
    this.productOrder.quantity += offset
  }

  nextStep() {
    this.checkoutStepper.next();
  }

  previousStep() {
    this.checkoutStepper.previous();
  }

  validateNextStep() {
    this.submitted = true;

    if (this.form.valid) {
      this.nextStep();
    }
    const currentDay = new Date()
    currentDay.setHours(0);
    currentDay.setMinutes(0);
    currentDay.setSeconds(0);
    const giveBackDaySplit = (this.form.get('giveBackDay').value as string).split("-")
    const giveBackDay = new Date([giveBackDaySplit[1], giveBackDaySplit[0], giveBackDaySplit[2]].join("/"))
    const diffTime = Math.abs(giveBackDay.getTime() - currentDay.getTime());
    this.diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24)) + 1;
  }

  getPrice() {
    return this.product.price * this.productOrder.quantity * this.diffDays
  }

  getDeposit() {
    return this.product.deposit * this.productOrder.quantity
  }

  getTotal() {
    return this.getPrice() + this.getDeposit()
  }

  onSubmit() {
    const order = {
      ...this.form.value,
      payment: this.payment,
      listOrderProduct: [this.productOrder],
      listSelectedCartDetailId: [],
    };
    this._orderService.create(order).subscribe(() => {
      this._router.navigate(["/order"]);
      this.modal.close()
      this._toastrService.success("", "Thành công", {
        toastClass: "toast ngx-toastr",
        closeButton: true,
      });
    });
  }
}
