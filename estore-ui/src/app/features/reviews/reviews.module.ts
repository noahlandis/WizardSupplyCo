import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';

import { MaterialModule } from 'src/app/material.module';
import { ReviewCardComponentComponent } from './review-card-component/review-card-component.component';



@NgModule({
  declarations: [
    ReviewCardComponentComponent
  ],
  imports: [
    CommonModule,
    MaterialModule
  ],
  exports: [
    ReviewCardComponentComponent
  ]
})
export class ReviewsModule { }
