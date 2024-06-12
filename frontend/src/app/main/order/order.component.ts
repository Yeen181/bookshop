import { Component, OnInit, ViewChild, ViewEncapsulation } from "@angular/core";
import { FormBuilder, FormGroup } from "@angular/forms";
import { AuthenticationService } from "app/auth/service";
import { Order } from "app/models/order";
import { Page } from "app/models/page";
import { debounceTime, distinctUntilChanged, startWith } from "rxjs/operators";
import { OrderService } from "./order.service";
import { Observable, forkJoin } from "rxjs";
import { ToastrService } from "ngx-toastr";
import Swal from "sweetalert2";
import { NgbModal, NgbModalRef } from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: "app-order",
  templateUrl: "./order.component.html",
  styleUrls: ["./order.component.scss"],
  encapsulation: ViewEncapsulation.None,
})
export class OrderComponent implements OnInit {
  @ViewChild("modal") public modal: NgbModalRef;
  public page: Page<Order> = new Page<Order>();
  public selected: Order[] = [];
  public form: FormGroup;
  public isAdmin = false;
  public order: Order;

  constructor(
    private _orderService: OrderService,
    private _fb: FormBuilder,
    private _authService: AuthenticationService,
    private _toastrService: ToastrService,
    private _modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.isAdmin = this._authService.isAdmin;

    this.form = this._fb.group({
      searchText: "",
      userId: this.isAdmin ? "" : this._authService.currentUserValue.id,
      page: 0,
      limit: 10,
    });

    this.form
      .get("searchText")
      .valueChanges.pipe(debounceTime(500), distinctUntilChanged())
      .subscribe(() => {
        this.form.patchValue({
          page: 0,
        });
      });

    this.form
      .get("page")
      .valueChanges.pipe(startWith(0))
      .subscribe(() => {
        setTimeout(() => {
          this.getOrders();
        });
      });
  }

  onSelect({ selected }) {
    this.selected.splice(0, this.selected.length);
    this.selected.push(...selected);
  }

  pageChange(event) {
    this.form.patchValue({
      page: event.offset,
    });
  }

  getId(row: Order) {
    return row.id;
  }

  getOrders() {
    this._orderService.search(this.form.value).subscribe((res) => {
      this.page = res.data;
    });
  }

  updateOrder() {
    if (this.selected.length == 0) {
      this._toastrService.warning("", "Chọn ít nhất 01 bản ghi", {
        toastClass: "toast ngx-toastr",
        closeButton: true,
      });
      return;
    }
    let obs: Observable<any>[] = [];
    for (let order of this.selected)
      obs.push(this._orderService.updateStatus(order.id));

    Swal.fire({
      title: "Xác nhận duyệt",
      text: "Không thể hoàn tác",
      icon: "warning",
      showCancelButton: true,
    }).then((result) => {
      if (result.value) {
        forkJoin(obs).subscribe(() => {
          this._toastrService.success("", "Thành công", {
            toastClass: "toast ngx-toastr",
            closeButton: true,
          });
          this.getOrders();
          this.selected = [];
        });
      }
    });
  }

  onActivate(event) {
    if (
      !event.event.ctrlKey &&
      event.event.type === "click" &&
      event.column.name != "."
    ) {
      this.order = event.row;
      this._modalService.open(this.modal, {
        centered: true,
        size: "lg",
      });
    }
  }
}
