import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { DashboardComponent } from "./dashboard.component";
import { RouterModule, Routes } from "@angular/router";
import { NgApexchartsModule } from "ng-apexcharts";
import { DetailComponent } from './detail/detail.component';

const routes: Routes = [
  {
    path: "",
    component: DashboardComponent,
  },
];

@NgModule({
  declarations: [DashboardComponent, DetailComponent],
  imports: [CommonModule, RouterModule.forChild(routes), NgApexchartsModule],
})
export class DashboardModule {}
