import { Category } from "./category";

export class Product {
  id: number;
  name: string;
  describe: string;
  price: number;
  image: string;
  quantity: number;
  deposit: number;
  category: Category;
  author: string
  isChecked: boolean = false;
}
