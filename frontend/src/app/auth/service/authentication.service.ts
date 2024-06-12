import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { BehaviorSubject, Observable } from "rxjs";
import { map, switchMap } from "rxjs/operators";

import { environment } from "environments/environment";
import { User, Role } from "app/auth/models";
import { ToastrService } from "ngx-toastr";
import { Response } from "app/models/response";

@Injectable({ providedIn: "root" })
export class AuthenticationService {
  //public
  public currentUser: Observable<User>;

  //private
  private currentUserSubject: BehaviorSubject<User>;

  /**
   *
   * @param {HttpClient} _http
   * @param {ToastrService} _toastrService
   */
  constructor(
    private _http: HttpClient,
    private _toastrService: ToastrService
  ) {
    this.currentUserSubject = new BehaviorSubject<User>(
      JSON.parse(localStorage.getItem("currentUser"))
    );
    this.currentUser = this.currentUserSubject.asObservable();
  }

  // getter: currentUserValue
  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }

  /**
   *  Confirms if user is admin
   */
  get isAdmin() {
    return (
      this.currentUser && this.currentUserSubject.value.role === Role.Admin
    );
  }

  /**
   *  Confirms if user is user
   */
  get isUser() {
    return this.currentUser && this.currentUserSubject.value.role === Role.User;
  }

  /**
   * User login
   *
   * @param email
   * @param password
   * @returns user
   */
  login(body) {
    const user = new User();

    return this._http
      .post<Response<any>>(`${environment.apiUrl}/authenticate`, body)
      .pipe(
        switchMap((res) => {
          user.token = res.data.token;
          this.currentUserSubject.next(user);
          return this._http
            .get<Response<User>>(`${environment.apiUrl}/user/info`)
            .pipe(map((res) => res.data));
        }),
        map((res) => {
          user.username = res.username;
          user.fullName = res.fullName;
          user.role = res.role;
          user.id = res.id;
          user.address = res.address
          user.phoneNumber = res.phoneNumber

          // login successful if there's a jwt token in the response
          if (user && user.token) {
            // store user details and jwt token in local storage to keep user logged in between page refreshes
            localStorage.setItem("currentUser", JSON.stringify(user));

            // Display welcome toast!
            setTimeout(() => {
              this._toastrService.success(
                "",
                "👋 Xin chào, " + user.fullName + "!",
                { toastClass: "toast ngx-toastr", closeButton: true }
              );
            }, 0);

            // notify
            this.currentUserSubject.next(user);
          }

          return user;
        })
      );
  }

  /**
   * User logout
   *
   */
  logout() {
    // remove user from local storage to log user out
    localStorage.removeItem("currentUser");
    // notify
    this.currentUserSubject.next(null);
  }

  register(body) {
    return this._http.post<Response<User>>(
      `${environment.apiUrl}/user/create`,
      body
    );
  }
}
