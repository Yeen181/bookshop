import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewEncapsulation,
} from "@angular/core";
import { CartDetail } from "app/models/cart-detail";
import { ToastrService } from "ngx-toastr";
import Swal from "sweetalert2";
import { CartService } from "../cart.service";

@Component({
  selector: "app-item",
  templateUrl: "./item.component.html",
  styleUrls: ["./item.component.scss"],
  encapsulation: ViewEncapsulation.None,
})
export class ItemComponent implements OnInit {
  @Input() cartDetail: CartDetail;
  @Output() onUpdate = new EventEmitter<void>();

  constructor(
    private _cartService: CartService,
    private _toastrService: ToastrService
  ) { }

  removeFromCart() {
    Swal.fire({
      title: "Xác nhận xóa",
      text: "Không thể hoàn tác",
      icon: "warning",
      showCancelButton: true,
    }).then((result) => {
      if (result.value) {
        this._cartService
          .removeProduct(this.cartDetail.id)
          .subscribe(() => {
            this._toastrService.success("", "Thành công", {
              toastClass: "toast ngx-toastr",
              closeButton: true,
            });
            this.onUpdate.emit();
          });
      }
    });
  }

  ngOnInit(): void { }

  updateQuantity(offset: number) {
    this._cartService
      .updateQuantity(this.cartDetail.id, this.cartDetail.quantity + offset)
      .subscribe((res) => {
        this.cartDetail = res.data;
        this.onUpdate.emit();
      });
  }
}
