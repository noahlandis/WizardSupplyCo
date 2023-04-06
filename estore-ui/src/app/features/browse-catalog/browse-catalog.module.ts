import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { MaterialModule } from 'src/app/material.module';
import { CatalogComponent } from './catalog/catalog.component';
import { CatalogProductCardComponent } from './catalog-product-card/catalog-product-card.component';
import { ProductDetailsComponent } from './product-details/product-details.component';
import { ReviewsModule } from '../reviews/reviews.module';
import { ReviewCardComponentComponent } from '../reviews/review-card-component/review-card-component.component';

@NgModule({
  declarations: [
    CatalogComponent,
    CatalogProductCardComponent,
    ProductDetailsComponent,
  ],
  imports: [CommonModule, RouterModule, MaterialModule, ReviewsModule],
  exports: [
    CatalogComponent,
    CatalogProductCardComponent,
    ProductDetailsComponent,
  ],
})
export class BrowseCatalogModule {}
