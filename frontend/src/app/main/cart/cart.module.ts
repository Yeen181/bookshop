import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CartComponent } from './cart.component';
import { RouterModule, Routes } from '@angular/router';
import { ContentHeaderModule } from 'app/layout/components/content-header/content-header.module';
import { CoreCommonModule } from '@core/common.module';
import { ItemComponent } from './item/item.component';
import { CoreTouchspinModule } from '@core/components/core-touchspin/core-touchspin.module';
import { Ng2FlatpickrModule } from "ng2-flatpickr";
import { NouisliderModule } from "ng2-nouislider";

const routes: Routes = [
  {
    path: '',
    component: CartComponent
  }
]

@NgModule({
  declarations: [
    CartComponent,
    ItemComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    ContentHeaderModule,
    CoreCommonModule,
    CoreTouchspinModule,
    Ng2FlatpickrModule,
    NouisliderModule
  ]
})
export class CartModule { }
