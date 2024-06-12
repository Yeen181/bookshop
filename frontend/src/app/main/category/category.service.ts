import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { CrudService } from "@core/services/crud.service";
import { Category } from "app/models/category";

@Injectable({
  providedIn: "root",
})
export class CategoryService extends CrudService<Category> {
  constructor(protected _http: HttpClient) {
    super(_http, "category");
  }
}
