import { Product } from "./product";

export class Order {
  id: number;
  listOrderDetail: {
    id: number;
    image: string;
    price: number;
    productName: string;
    quantity: number;
  }[];
  total: number;
  note: string;
  payment: string;
  receiverName: string;
  receiverPhone: string;
  receiverAddress: string;
  giveBackDay: Date;
  timeOrder: Date;
  status: string;
}
