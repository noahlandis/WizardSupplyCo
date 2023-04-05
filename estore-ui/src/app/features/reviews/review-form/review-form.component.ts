import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { authAdminGuard } from 'src/app/core/guards/auth-admin.guard';
import { AuthService } from 'src/app/core/services/auth.service';
import { BaseReview,  Review } from 'src/app/core/model/review.model';
import { OrdersService } from 'src/app/core/services/orders.service';
import { ReviewService } from 'src/app/core/services/review.service';


@Component({
  selector: 'app-review-form',
  templateUrl: './review-form.component.html',
  styleUrls: ['./review-form.component.css']
})
export class ReviewFormComponent {

  reviewForm : FormGroup;
  stars: number[] = [1, 2, 3, 4, 5];
  public sku: number = 0;
  private userId = this.authService.getUserId() || 0;
  purchasedSkus: number[] = [];
  private routeSubscription!: Subscription;



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
    });
  }

  public userHasBoughtProduct(): boolean {
    let hasBought = false;
    this.ordersService.getProductsOrderedByUserId(this.userId).subscribe(
      purchasedSkus => { this.purchasedSkus = purchasedSkus;
      this.purchasedSkus.forEach(sku => {
        console.log('sku: ' + sku);
        if (sku == this.sku) {
          hasBought = true;
        }
      });  
    });
    return hasBought;
  }  

  async create() {
    if(!this.reviewForm.valid)
      return;
    this.userId = this.authService.getUserId() || 0;
    const rating = this.reviewForm.value.rating;
    const comment = this.reviewForm.value.comment;
    const review = new BaseReview(
      this.userId,
      this.sku,
      rating,
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

