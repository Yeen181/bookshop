import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewChild,
  ViewEncapsulation,
} from "@angular/core";
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { NgbActiveModal, NgbModal, NgbModalRef } from "@ng-bootstrap/ng-bootstrap";
import { CategoryService } from "app/main/category/category.service";
import { Category } from "app/models/category";
import { HomeService } from "../home.service";
import { ToastrService } from "ngx-toastr";
import { Product } from "app/models/product";

@Component({
  selector: "app-create",
  templateUrl: "./create.component.html",
  styleUrls: ["./create.component.scss"],
  encapsulation: ViewEncapsulation.None,
})
export class CreateComponent implements OnInit {
  @Input("modal") public modal: NgbActiveModal;
  @Input("product") public product: Product;
  @Output("onSuccess") public emitter = new EventEmitter<Product>();
  @Output("onSuccess2") public emitter2 = new EventEmitter<void>();
  @ViewChild('modal2') public modal2: NgbModalRef
  public categories: Category[] = [];
  public form: FormGroup;
  public categoryFormControl = new FormControl('', [Validators.required])
  public submitted = false
  public submitted2 = false

  get f() {
    return this.form.controls
  }

  constructor(
    private _categoryService: CategoryService,
    private _fb: FormBuilder,
    private _homeService: HomeService,
    private _toastrService: ToastrService,
    private _modalService: NgbModal
  ) { }

  ngOnInit(): void {
    this.form = this._fb.group({
      name: ["", Validators.required],
      describe: ["", Validators.required],
      price: [null, Validators.required],
      categoryId: [null, Validators.required],
      deposit: [null, Validators.required],
      quantity: [null, Validators.required],
      file: ["", Validators.required],
      author: ["", Validators.required],
    });
    if (this.product)
      this.form.patchValue({ ...this.product, categoryId: this.product.category.id, file: this.product.image })

    this.getCategories()
  }

  getCategories() {
    this._categoryService.getAll().subscribe((res) => {
      this.categories = res.data;
    });
  }

  changeImage(event: Event) {
    const target = event.target as HTMLInputElement;
    this.form.patchValue({ file: target.files[0] });
  }

  onSubmit() {
    this.submitted = true

    if (this.form.invalid) return

    if (this.product) {
      this._homeService.update(this.form.value, this.product.id).subscribe((res) => {
        this._toastrService.success("", "Thành công", {
          toastClass: "toast ngx-toastr",
          closeButton: true,
        });
        this.modal.dismiss();
        this.emitter.emit(res.data);
      });
    } else {
      const formData = new FormData();
      for (let key of Object.keys(this.form.value))
        formData.append(key, this.form.get(key).value);
      this._homeService.create(formData).subscribe((res) => {
        this._toastrService.success("", "Thành công", {
          toastClass: "toast ngx-toastr",
          closeButton: true,
        });
        this.modal.dismiss();
        this.emitter.emit(res.data);
      });
    }

  }

  openModal(modal) {
    this.submitted2 = false
    this.categoryFormControl.reset()
    this.modal2 = this._modalService.open(modal, { centered: true })
  }

  onCategoryFormSubmit() {
    this.submitted2 = true

    if (this.categoryFormControl.invalid)
      return

    this._categoryService.create({ name: this.categoryFormControl.value }).subscribe((res) => {
      this._toastrService.success("", "Thành công", {
        toastClass: "toast ngx-toastr",
        closeButton: true,
      });
      this.modal2.close();
      this.emitter2.emit()
      this.getCategories()
    });
  }
}
