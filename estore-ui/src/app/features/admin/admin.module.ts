import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { MaterialModule } from 'src/app/material.module';

import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { AdminProductCardComponent } from './admin-product-card/admin-product-card.component';
import { CreateProductComponent } from './create-product/create-product.component';
import { EditProductComponent } from './edit-product/edit-product.component';


@NgModule({
  declarations: [
    AdminDashboardComponent,
    AdminProductCardComponent,
    CreateProductComponent,
    EditProductComponent,
  ],
  imports: [
    CommonModule,
    RouterModule,
    MaterialModule,
    ReactiveFormsModule,
  ],
  exports: [
    AdminDashboardComponent,
    AdminProductCardComponent,
    CreateProductComponent,
    EditProductComponent,
  ]
})
export class AdminModule { }
