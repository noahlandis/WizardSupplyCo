import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { MaterialModule } from 'src/app/material.module';

import { CartComponent } from './cart/cart.component';
import { CartProductCardComponent } from './cart-product-card/cart-product-card.component';

@NgModule({
  declarations: [CartComponent, CartProductCardComponent],
  imports: [
    CommonModule,
    RouterModule,
    MaterialModule,
    ReactiveFormsModule,
  ],
  exports: [
    CartComponent,
    CartProductCardComponent
  ]
})
export class OrderProcessingModule { }
