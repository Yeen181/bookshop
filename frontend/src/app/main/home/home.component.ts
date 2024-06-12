import { Component, OnInit, ViewChild, ViewEncapsulation } from "@angular/core";
import { CoreSidebarService } from "@core/components/core-sidebar/core-sidebar.service";
import { HomeService } from "./home.service";
import { Page } from "app/models/page";
import { Product } from "app/models/product";
import { FormBuilder, FormGroup } from "@angular/forms";
import { debounceTime, distinctUntilChanged } from "rxjs/operators";
import { NgbModal, NgbModalRef } from "@ng-bootstrap/ng-bootstrap";
import { Observable, forkJoin } from "rxjs";
import { ToastrService } from "ngx-toastr";
import Swal from "sweetalert2";
import { AuthenticationService } from "app/auth/service";
import { CategoryService } from "../category/category.service";
import { Category } from "app/models/category";

@Component({
  selector: "app-home",
  templateUrl: "./home.component.html",
  styleUrls: ["./home.component.scss"],
  encapsulation: ViewEncapsulation.None,
  host: { class: "ecommerce-application" },
})
export class HomeComponent implements OnInit {
  @ViewChild("modal") public modal: NgbModalRef;
  public form: FormGroup;
  public page = new Page<Product>();
  public categories: Category[] = []
  public isAdmin: boolean

  get f() {
    return this.form.controls;
  }

  constructor(
    private _coreSidebarService: CoreSidebarService,
    private _homeService: HomeService,
    private _fb: FormBuilder,
    private _modalService: NgbModal,
    private _toastrService: ToastrService,
    private _authService: AuthenticationService,
    private _categoryService: CategoryService
  ) { }

  toggleSidebar(name): void {
    this._coreSidebarService.getSidebarRegistry(name).toggleOpen();
  }

  ngOnInit(): void {
    this.isAdmin = this._authService.isAdmin

    this.form = this._fb.group({
      searchText: "",
      categoryId: "",
      priceFrom: "",
      priceTo: "",
      page: 0,
      limit: 6,
    });

    this.form
      .get("searchText")
      .valueChanges.pipe(debounceTime(500), distinctUntilChanged())
      .subscribe(() => {
        setTimeout(() => {
          this._homeService.onFilterChange.next(this.form.value);
        });
      });
    this.getCategories()
    this._homeService.onFilterChange.subscribe((res) => {
      this.form.patchValue({ ...res });
      this.getProducts();
    });
  }

  pageChange(event: number) {
    this.form.patchValue({ page: event - 1 });
    this._homeService.onFilterChange.next(this.form.value);
  }

  getProducts() {
    this._homeService.search(this.form.value).subscribe((res) => {
      this.page = res.data;
      this.page.data = res.data.data.map((item) => {
        return {
          ...item,
          isChecked: false,
        };
      });
    });
  }

  getCategories() {
    this._categoryService.getAll().subscribe((res) => {
      this.categories = res.data;
    });
  }

  add() {
    this._modalService.open(this.modal, { centered: true, size: "lg" });
  }

  delete() {
    let obs: Observable<any>[] = [];
    for (let product of this.page.data) {
      if (product.isChecked) {
        obs.push(this._homeService.delete(product.id));
      }
    }

    if (obs.length == 0)
      this._toastrService.warning("", "Chọn ít nhất 1 bản ghi", {
        toastClass: "toast ngx-toastr",
        closeButton: true,
      });
    else
      Swal.fire({
        title: 'Xác nhận xóa',
        text: "Không thể hoàn tác",
        icon: 'warning',
        showCancelButton: true,
      }).then((result) => {
        if (result.value) {
          forkJoin(obs).subscribe(() => {
            this._toastrService.success("", "Thành công", {
              toastClass: "toast ngx-toastr",
              closeButton: true,
            });
            this.getProducts();
          });

        }
      });
  }
}
