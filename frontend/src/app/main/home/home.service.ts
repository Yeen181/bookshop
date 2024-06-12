import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { CrudService } from "@core/services/crud.service";
import { Product } from "app/models/product";
import { Response } from "app/models/response";
import { BehaviorSubject, Observable } from "rxjs";

@Injectable({
  providedIn: "root",
})
export class HomeService extends CrudService<Product> {
  public onFilterChange: BehaviorSubject<any>;

  constructor(protected _http: HttpClient) {
    super(_http, "product");
    this.onFilterChange = new BehaviorSubject({});
  }

  // update(body: any, id: string) {
  //   return this._http.put<Response<Product>>(`${this._path}/update`, body)
  // }
}
