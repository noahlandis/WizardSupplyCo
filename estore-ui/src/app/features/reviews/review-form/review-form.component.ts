import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { authAdminGuard } from 'src/app/core/guards/auth-admin.guard';
import { AuthService } from 'src/app/core/services/auth.service';
//import { BaseReview,  Review } from 'src/app/core/models/review-model';
import { OrdersService } from 'src/app/core/services/orders.service';
//import { ReviewService } from 'src/app/core/services/review.service';


@Component({
  selector: 'app-review-form',
  templateUrl: './review-form.component.html',
  styleUrls: ['./review-form.component.css']
})
export class ReviewFormComponent {

  reviewForm : FormGroup;
  stars: number[] = [1, 2, 3, 4, 5];

  constructor(
    public authService: AuthService,
    //public authAdminGuard: authAdminGuard,
    //public reviewService: ReviewService,
    public formBuilder: FormBuilder,
    public ordersService: OrdersService,
    private snackBar: MatSnackBar

  ) {
      this.reviewForm = this.formBuilder.group({
      rating: ['', Validators.required],
      comment: ['', []],
    });
   }

  ngOnInit(): void {    
  }

  async create(): Promise<void> {
    if(!this.reviewForm.valid)
      return;

    const review = new BaseReview(
      this.authService.getUserId(),
      //sku,
      this.reviewForm.value.rating,
      this.reviewForm.value.comment  
    );
    console.log('Create review form submitted with review:', JSON.stringify(review));

    this.reviewService.createReview(review).subscribe({
      next: async (createSuccess) => {
        if (createSuccess) {
          console.log('Review created successfully');
          this.displaySnackBar('Review created successfully', 'Dismiss', 3000);
        } else {
          console.log('Review creation failed');
          this.displaySnackBar('Review creation failed', 'Dismiss', 3000);
        }
      },
      error: (e) => {
        console.log('Review creation failed with error:', e);
        this.displaySnackBar('Review creation failed', 'Dismiss', 3000);
      },
  });
  }

  /** Display snackbar notification */
  displaySnackBar(message: string, action: string, duration: number, error?: boolean) {
    this.snackBar.open(message, action, {
      duration: duration
    });
  }
}

