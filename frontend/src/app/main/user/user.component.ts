import { Component, OnInit, ViewEncapsulation } from "@angular/core";
import { UserService } from "./user.service";
import { FormBuilder, FormGroup } from "@angular/forms";
import { Page } from "app/models/page";
import { User } from "app/models/user";
import { debounceTime, distinctUntilChanged, startWith } from "rxjs/operators";
import { ToastrService } from "ngx-toastr";
import Swal from "sweetalert2";
import { Observable, forkJoin } from "rxjs";

@Component({
  selector: "app-user",
  templateUrl: "./user.component.html",
  styleUrls: ["./user.component.scss"],
  encapsulation: ViewEncapsulation.None,
})
export class UserComponent implements OnInit {
  public form: FormGroup;
  public page = new Page<User>();
  public selected: User[] = [];

  constructor(
    private _userService: UserService,
    private _fb: FormBuilder,
    private _toastrService: ToastrService
  ) {}

  ngOnInit(): void {
    this.form = this._fb.group({
      page: 0,
      limit: 5,
      searchText: "",
      active: true,
    });

    this.form
      .get("searchText")
      .valueChanges.pipe(debounceTime(500), distinctUntilChanged())
      .subscribe(() => {
        this.form.patchValue({
          page: 0,
        });
      });

    this.form
      .get("page")
      .valueChanges.pipe(startWith(0))
      .subscribe(() => {
        setTimeout(() => {
          this.getUsers();
        });
      });
  }

  getUsers() {
    this._userService.search(this.form.value).subscribe((res) => {
      this.page = res.data;
    });
  }

  pageChange(event) {
    this.form.patchValue({
      page: event.offset,
    });
  }

  getId(row: User) {
    return row.id;
  }

  onSelect({ selected }) {
    this.selected.splice(0, this.selected.length);
    this.selected.push(...selected);
  }

  delete() {
    if (this.selected.length == 0) {
      this._toastrService.warning("", "Chọn ít nhất 01 bản ghi", {
        toastClass: "toast ngx-toastr",
        closeButton: true,
      });
      return;
    }
    let obs: Observable<any>[] = [];
    for (let user of this.selected)
      obs.push(this._userService.delete(user.username));

    Swal.fire({
      title: "Xác nhận xóa",
      text: "Không thể hoàn tác",
      icon: "warning",
      showCancelButton: true,
    }).then((result) => {
      if (result.value) {
        forkJoin(obs).subscribe(() => {
          this._toastrService.success("", "Thành công", {
            toastClass: "toast ngx-toastr",
            closeButton: true,
          });
          this.getUsers();
          this.selected = [];
        });
      }
    });
  }
}
