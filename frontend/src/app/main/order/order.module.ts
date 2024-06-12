import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { OrderComponent } from "./order.component";
import { RouterModule, Routes } from "@angular/router";
import { CoreCommonModule } from "@core/common.module";
import { NgSelectModule } from "@ng-select/ng-select";
import { DetailComponent } from './detail/detail.component';

const routes: Routes = [
  {
    path: "",
    component: OrderComponent,
  },
];

@NgModule({
  declarations: [OrderComponent, DetailComponent],
  imports: [CommonModule, RouterModule.forChild(routes), CoreCommonModule, NgSelectModule],
})
export class OrderModule {}
