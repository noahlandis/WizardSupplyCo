import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../../material.module';

import { MaterialModule } from 'src/app/material.module';
import { ReviewCardComponent } from './review-card/review-card.component';
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
    ReviewFormComponent
  ]
})
export class ReviewsModule { }
