import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Page } from "app/models/page";
import { Response } from "app/models/response";
import { environment } from "environments/environment";

@Injectable({
  providedIn: "root",
})
export class CrudService<T> {
  protected _path: string;
  constructor(protected _http: HttpClient, _endpoint: string) {
    this._path = `${environment.apiUrl}/${_endpoint}`;
  }

  search(filter: { [param: string]: string | number | boolean | null }) {
    let params = new HttpParams({ fromObject: filter });
    params = params.set("isPaginate", true);

    return this._http.get<Response<Page<T>>>(`${this._path}/search`, {
      params: params,
    });
  }

  searchAll(filter?: { [param: string]: string | number | boolean | null }) {
    let params = new HttpParams({ fromObject: filter });
    params = params.set("isPaginate", false);

    return this._http.get<Response<T[]>>(`${this._path}/search`, {
      params: params,
    });
  }

  getAll() {
    return this._http.get<Response<T[]>>(`${this._path}/`);
  }

  getById(id: number) {
    return this._http.get<Response<T>>(`${this._path}/${id}`);
  }

  create(body) {
    return this._http.post<Response<T>>(`${this._path}/create`, body);
  }

  update(body, id: number) {
    return this._http.put<Response<T>>(`${this._path}/update/${id}`, body);
  }

  delete(id: number | string) {
    return this._http.delete<Response<T>>(`${this._path}/delete/${id}`);
  }
}
