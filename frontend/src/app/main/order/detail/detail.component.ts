import { Component, Input, OnInit } from "@angular/core";
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";
import { Order } from "app/models/order";

@Component({
  selector: "app-detail",
  templateUrl: "./detail.component.html",
  styleUrls: ["./detail.component.scss"],
})
export class DetailComponent implements OnInit {
  @Input("order") public order: Order;
  @Input('modal') public modal: NgbActiveModal

  constructor() { }

  ngOnInit(): void {
  }
}
