import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Cart } from "app/models/cart";
import { CartDetail } from "app/models/cart-detail";
import { Response } from "app/models/response";
import { environment } from "environments/environment";

@Injectable({
  providedIn: "root",
})
export class CartService {
  constructor(private _http: HttpClient) {}

  getAll() {
    return this._http.get<Response<CartDetail[]>>(
      `${environment.apiUrl}/cart/current`
    );
  }

  addProduct(productId: number) {
    return this._http.post(`${environment.apiUrl}/cart/add-product`, {
      productId,
      quantity: 1,
    });
  }

  removeProduct(productId: number) {
    return this._http.delete(
      `${environment.apiUrl}/cart/remove-product/${productId}`
    );
  }

  updateQuantity(cartDetailId: number, quantity: number) {
    return this._http.post<Response<CartDetail>>(`${environment.apiUrl}/cart/update-quantity`, {
      cartDetailId,
      quantity,
    });
  }
}
