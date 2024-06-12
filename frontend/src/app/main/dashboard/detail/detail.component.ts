import { Component, Input, OnInit } from "@angular/core";
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";
import { OrderService } from "app/main/order/order.service";
import { Order } from "app/models/order";

@Component({
  selector: "app-detail",
  templateUrl: "./detail.component.html",
  styleUrls: ["./detail.component.scss"],
})
export class DetailComponent implements OnInit {
  @Input("month") public month: number;
  @Input("modal") public modal: NgbActiveModal;
  public orders: Order[] = [];
  public total = 0;

  constructor(private _orderService: OrderService) {}

  ngOnInit(): void {
    this._orderService.searchAll({ month: this.month }).subscribe((res) => {
      this.orders = res.data;
      this.orders.forEach((item) => {
        this.total += item.total;
      });
    });
  }

  export() {
    const separator = ",";
    const keys = [
      "STT",
      "ID",
      "Người nhận",
      "Ngày tạo",
      "Ngày trả hàng",
      "Số tiền (VNĐ)",
    ];
    const csvData =
      keys.join(separator) +
      "\n" +
      this.orders
        .map((item: Order, index) => {
          return [
            index + 1,
            item.id,
            item.receiverName,
            item.timeOrder,
            item.giveBackDay,
            item.total,
          ].join(separator);
        })
        .join("\n") +
      `\nTổng,,,,,${this.total}`;
    const blob = new Blob(["\ufeff" + csvData], {
      type: "text/csv;charset=utf-8;",
    });
    const link = document.createElement("a");
    if (link.download !== undefined) {
      const url = URL.createObjectURL(blob);
      link.setAttribute("href", url);
      link.setAttribute("download", "Đơn hàng");
      link.style.visibility = "hidden";
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    }
  }
}
