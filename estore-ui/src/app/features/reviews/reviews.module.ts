import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';

import { MaterialModule } from 'src/app/material.module';
import { ReviewCardComponent } from './review-card/review-card.component';
import { ReviewCardComponentComponent } from './review-card-component/review-card-component.component';



@NgModule({
  declarations: [
    ReviewCardComponentComponent
  ],
  imports: [
    CommonModule
  ]
})
export class ReviewsModule { }
