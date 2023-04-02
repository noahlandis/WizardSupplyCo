import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { MaterialModule } from 'src/app/material.module';
import { CatalogComponent } from './catalog/catalog.component';
import { CatalogProductCardComponent } from './catalog-product-card/catalog-product-card.component';
import { ProductDetailsComponent } from './product-details/product-details.component';

@NgModule({
  declarations: [CatalogComponent, CatalogProductCardComponent, ProductDetailsComponent],
  imports: [
    CommonModule,
    RouterModule,
    MaterialModule,
  ],
  exports: [
    CatalogComponent,
    CatalogProductCardComponent,
    ProductDetailsComponent
  ]
})
export class BrowseCatalogModule { }
