import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { RouterModule, Routes } from "@angular/router";
import { CoreCommonModule } from "@core/common.module";
import { CoreSidebarModule } from "@core/components";
import { CoreTouchspinModule } from "@core/components/core-touchspin/core-touchspin.module";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { ContentHeaderModule } from "app/layout/components/content-header/content-header.module";
import {
  SWIPER_CONFIG,
  SwiperConfigInterface,
  SwiperModule,
} from "ngx-swiper-wrapper";
import { HomeComponent } from "./home.component";

import { NgSelectModule } from "@ng-select/ng-select";
import { CreateComponent } from "./create/create.component";
import { DetailComponent } from "./detail/detail.component";
import { FilterComponent } from "./filter/filter.component";
import { ItemComponent } from "./item/item.component";
import { BuyNowComponent } from './buy-now/buy-now.component';
import { Ng2FlatpickrModule } from "ng2-flatpickr";

const DEFAULT_SWIPER_CONFIG: SwiperConfigInterface = {
  direction: "horizontal",
  observer: true,
};

const routes: Routes = [
  {
    path: "",
    component: HomeComponent,
  },
];

@NgModule({
  declarations: [
    HomeComponent,
    FilterComponent,
    ItemComponent,
    DetailComponent,
    CreateComponent,
    BuyNowComponent,
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    SwiperModule,
    FormsModule,
    CoreTouchspinModule,
    ContentHeaderModule,
    CoreSidebarModule,
    CoreCommonModule,
    NgbModule,
    NgSelectModule,
    Ng2FlatpickrModule
  ],
  providers: [
    {
      provide: SWIPER_CONFIG,
      useValue: DEFAULT_SWIPER_CONFIG,
    },
  ],
})
export class HomeModule { }
