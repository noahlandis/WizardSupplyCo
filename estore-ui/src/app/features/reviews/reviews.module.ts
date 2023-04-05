import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../../material.module';

import { ReviewFormComponent } from './review-form/review-form.component';


@NgModule({
  declarations: [ReviewFormComponent],
  imports: [
    CommonModule,
    RouterModule,
    MaterialModule,
    ReactiveFormsModule,
  ],
  exports: [
    ReviewFormComponent
  ]
})
export class ReviewsModule { }
