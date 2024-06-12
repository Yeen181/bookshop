import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { CrudService } from "@core/services/crud.service";
import { Order } from "app/models/order";

@Injectable({
  providedIn: "root",
})
export class OrderService extends CrudService<Order> {
  constructor(protected _http: HttpClient) {
    super(_http, "order");
  }

  updateStatus(orderId: number) {
    return this._http.post(`${this._path}/update-status`, {
      orderId,
      status: "SHIPPING",
    });
  }
}
