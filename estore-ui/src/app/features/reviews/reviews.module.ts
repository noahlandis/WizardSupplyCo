import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../../material.module';

import { ReviewCardComponentComponent } from './review-card-component/review-card-component.component';
import { ReviewFormComponent } from './review-form/review-form.component';


@NgModule({
  declarations: [ReviewFormComponent,
    ReviewCardComponentComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    MaterialModule,
    ReactiveFormsModule,
  ],
  exports: [
    ReviewFormComponent,
    ReviewCardComponentComponent
  ]
})
export class ReviewsModule { }
