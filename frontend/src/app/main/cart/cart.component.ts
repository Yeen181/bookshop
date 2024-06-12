import { Component, OnInit, ViewEncapsulation } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { CartDetail } from "app/models/cart-detail";
import Stepper from "bs-stepper";
import { CartService } from "./cart.service";
import { ToastrService } from "ngx-toastr";
import { FlatpickrOptions } from "ng2-flatpickr";
import { OrderService } from "../order/order.service";
import { DatePipe } from "@angular/common";
import { Router } from "@angular/router";
import { AuthenticationService } from "app/auth/service";
import { Product } from "app/models/product";

@Component({
  selector: "app-cart",
  templateUrl: "./cart.component.html",
  styleUrls: ["./cart.component.scss"],
  encapsulation: ViewEncapsulation.None,
  host: { class: "ecommerce-application" },
})
export class CartComponent implements OnInit {
  public carts: CartDetail[] = [];
  public form: FormGroup;
  public submitted = false;
  private checkoutStepper: Stepper;
  public flatpickrConfig: FlatpickrOptions;
  public payment = "Mã QR";
  public diffDays = 0

  get f() {
    return this.form.controls;
  }

  constructor(
    private _cartService: CartService,
    private _fb: FormBuilder,
    private _toastrService: ToastrService,
    private _orderService: OrderService,
    private _router: Router,
    private _authService: AuthenticationService
  ) { }

  nextStep() {
    if (this.carts.length == 0) {
      this._toastrService.warning("", "Chưa có mặt hàng nào cần thanh toán", {
        toastClass: "toast ngx-toastr",
        closeButton: true,
      });
      return;
    }
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

  ngOnInit(): void {
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

    this.getCart();
  }

  getCart() {
    this._cartService.getAll().subscribe((res) => {
      this.carts = res.data
    });
  }

  getPrice(item: CartDetail) {
    return item.productDto.price * item.quantity * this.diffDays
  }

  getDeposit(item: CartDetail) {
    return item.productDto.deposit * item.quantity
  }

  getTotal() {
    let total = 0
    for (const item of this.carts)
      total += this.getPrice(item) + this.getDeposit(item)
    return total
  }

  onSubmit() {
    const order = {
      ...this.form.value,
      payment: this.payment,
      // listOrderProduct: this.carts.map((item) => {
      //   return {
      //     productId: item.productDto.id,
      //     quantity: item.quantity,
      //   };
      // }),
      listOrderProduct: [],
      listSelectedCartDetailId: this.carts.map((item) => item.id),
      // listSelectedCartDetailId: [],
    };
    this._orderService.create(order).subscribe(() => {
      this._router.navigate(["/order"]);
      this._toastrService.success("", "Thành công", {
        toastClass: "toast ngx-toastr",
        closeButton: true,
      });
    });
  }
}
