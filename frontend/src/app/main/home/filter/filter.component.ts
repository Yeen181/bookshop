import { Component, Input, OnInit, ViewEncapsulation } from "@angular/core";
import { FormBuilder, FormGroup } from "@angular/forms";
import { CategoryService } from "app/main/category/category.service";
import { Category } from "app/models/category";
import { HomeService } from "../home.service";

@Component({
  selector: "app-filter",
  templateUrl: "./filter.component.html",
  styleUrls: ["./filter.component.scss"],
  encapsulation: ViewEncapsulation.None,
})
export class FilterComponent implements OnInit {
  @Input('categories') public categories: Category[] = [];
  public form: FormGroup;

  constructor(

    private _fb: FormBuilder,
    private _homeService: HomeService
  ) { }

  ngOnInit(): void {
    this.form = this._fb.group({
      categoryId: "",
      priceFrom: "",
      priceTo: "",
    });


  }

  search() {
    this._homeService.onFilterChange.next(this.form.value);
  }

  reset() {
    this.form.patchValue({
      categoryId: "",
      priceFrom: "",
      priceTo: "",
    });
    this.search();
  }
}
