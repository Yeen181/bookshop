import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Response } from "app/models/response";
import { environment } from "environments/environment";

@Injectable({
  providedIn: "root",
})
export class DashboardService {
  constructor(private _http: HttpClient) { }

  getTopCategory() {
    return this._http.get<Response<any>>(
      `${environment.apiUrl}/dashboard/category`
    );
  }

  getRevenuePrice() {
    return this._http.get<Response<any>>(
      `${environment.apiUrl}/dashboard/revenue-price?year=2024`
    );
  }

  getRevenueTotal() {
    return this._http.get<Response<any>>(
      `${environment.apiUrl}/dashboard/revenue-total?year=2024`
    );
  }

  getOrderWithMonth(month: number) {
    return this._http.get<Response<any>>(
      `${environment.apiUrl}/dashboard/order-with-month`, { params: { month } }
    );
  }
}
