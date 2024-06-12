import {
  Component,
  Input,
  OnInit,
  ViewChild,
  ViewEncapsulation,
} from "@angular/core";
import { NgbModal, NgbModalRef } from "@ng-bootstrap/ng-bootstrap";
import { AuthenticationService } from "app/auth/service";
import { CartService } from "app/main/cart/cart.service";
import { Product } from "app/models/product";
import { ToastrService } from "ngx-toastr";

@Component({
  selector: "app-item",
  templateUrl: "./item.component.html",
  styleUrls: ["./item.component.scss"],
  encapsulation: ViewEncapsulation.None,
  host: { class: "ecommerce-application" },
})
export class ItemComponent implements OnInit {
  @Input() product: Product;
  public isAdmin = false;

  constructor(
    private _cartService: CartService,
    private _modalService: NgbModal,
    private _toastrService: ToastrService,
    private _authService: AuthenticationService
  ) { }


  ngOnInit(): void {
    this.isAdmin = this._authService.isAdmin
  }

  addToCart() {
    this._cartService.addProduct(this.product.id).subscribe(() => {
      this._toastrService.success("", "Thành công", {
        toastClass: "toast ngx-toastr",
        closeButton: true,
      });
    });
  }

  openDetail(modal, size) {
    this._modalService.open(modal, {
      centered: true,
      size: size,
    });
  }

  onUpdate(event: Product) {
    this.product = event
  }

}
