import { CoreMenu } from "@core/types";
import { Role } from "app/auth/models";

export const menu: CoreMenu[] = [
  {
    id: "home",
    title: "Trang chủ",
    type: "item",
    icon: "home",
    url: "home",
  },
  {
    id: "cart",
    title: "Giỏ hàng",
    type: "item",
    icon: "shopping-cart",
    url: "cart",
  },
  {
    id: "user",
    title: "Người dùng",
    type: "item",
    icon: "users",
    url: "user",
    role: [Role.Admin],
  },
  {
    id: "order",
    title: "Đơn thuê",
    type: "item",
    icon: "file-text",
    url: "order",
  },
  {
    id: "dashboard",
    title: "Thống kê",
    type: "item",
    icon: "pie-chart",
    url: "dashboard",
    role: [Role.Admin],
  },
];
