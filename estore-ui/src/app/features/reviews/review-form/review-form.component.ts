import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { Observable, Subscription, catchError, map } from 'rxjs';
import { authAdminGuard } from 'src/app/core/guards/auth-admin.guard';
import { AuthService } from 'src/app/core/services/auth.service';
import { BaseReview, Review } from 'src/app/core/model/review.model';
import { OrdersService } from 'src/app/core/services/orders.service';
import { ReviewService } from 'src/app/core/services/review.service';
import { ProductDetailsComponent } from '../../browse-catalog/product-details/product-details.component';


@Component({
  selector: 'app-review-form',
  templateUrl: './review-form.component.html',
  styleUrls: ['./review-form.component.css']
})
export class ReviewFormComponent {

  reviewForm: FormGroup;
  public stars: number[] = [1, 2, 3, 4, 5];
  public sku: number = 0;
  private userId = this.authService.getUserId() || 0;
  purchasedSkus: number[] = [];
  private routeSubscription!: Subscription;
  @Input('rating') public rating: number = 0;
  @Input('starCount') public starCount: number = 5;
  @Input('color') public color: string = 'accent';
  @Output() public ratingUpdated = new EventEmitter();
  public ratingArr: number[] = [];
  public ratingSelected: boolean = false;
  @ViewChild('app-product-details', { static: false }) productDetails!: ProductDetailsComponent;
  public showForm = true;

  constructor(
    public authService: AuthService,
    public reviewService: ReviewService,
    public formBuilder: FormBuilder,
    public ordersService: OrdersService,
    private snackBar: MatSnackBar,
    private route: ActivatedRoute,

  ) {
    this.reviewForm = this.formBuilder.group({
      rating: ['', Validators.required],
      comment: ['', []],
    });
  }

  ngOnInit(): void {
  this.routeSubscription = this.route.paramMap.subscribe(params => {
    this.sku = Number(params.get('sku'));
    this.userId = this.authService.getUserId() || 0;
    this.ratingArr = [...this.stars];

    this.checkIfSkuExists(this.userId).subscribe(
      reviewExists => {
        if (reviewExists) {
          this.showForm = false;
        }
      },
      error => {
        console.log('Error checking if SKU exists for user:', error);
      }
    );
  });
}

  async create() {
    if (!this.reviewForm.valid)
      return;
    const rating = this.rating;
    const comment = this.reviewForm.value.comment;
    const review = new BaseReview(
      this.userId,
      this.sku,
      rating,
      comment
    );
    console.log('Create review form submitted with review:', JSON.stringify(review));

    this.reviewService.createReview(review).subscribe({
      next: async (createSuccess) => {
        if (createSuccess) {
          this.productDetails.getReviews(this.sku);
          console.log('Review created successfully');
          this.showForm = false;
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

  onClick(rating: number) {
    this.rating = rating;
    this.ratingUpdated.emit(rating);
    this.reviewForm.controls['rating'].setValue(rating);
    this.ratingSelected = true;
    return false;
  }

  showIcon(index: number) {
    if (this.rating >= index) {
      return 'star';
    } else {
      return 'star_border';
    }
  }

  checkIfSkuExists(userId: number): Observable<boolean> {
    return this.reviewService.getReviewsByUserId(userId).pipe(
      map(reviews => {
        for (const review of reviews) {
          if (review.userId === userId) {
            return true;
          }
        }
        return false;
      })
    );
  }
}

export enum StarRatingColor {
  primary = "primary",
  accent = "accent",
  warn = "warn"
}



