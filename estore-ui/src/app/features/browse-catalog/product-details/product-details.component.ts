import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/core/services/auth.service';
import { CartsService } from 'src/app/core/services/carts.service';
import { InventoryService } from 'src/app/core/services/inventory.service';
import { ReviewFormComponent } from '../../reviews/review-form/review-form.component';
import { Review } from 'src/app/core/model/review.model';
import { ReviewService } from 'src/app/core/services/review.service';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.scss']
})
export class ProductDetailsComponent implements OnInit, OnDestroy {
  sku = 0;
  name!: string;
  summary!: string;
  image = '';
  stock = 0
  stockStatus: string | undefined
  price = 0
  tags: string[] = []
  placeholder: string = 'https://i.imgur.com/gJZTkgt.png';

  private readonly QUANTITY_LOW_STOCK = 10;
  private readonly QUANTITY_OUT_OF_STOCK = 0; 
  private routeSubscription!: Subscription;
  @ViewChild(ReviewFormComponent, {static : false}) reviewForm!: ReviewFormComponent;
  public reviews :Review[] = [];

  constructor(
    private route: ActivatedRoute,
    private inventoryService: InventoryService,
    private cartsService: CartsService,
    public authService: AuthService,
    private reviewService: ReviewService
  ) {}

  ngOnInit(): void {
    this.routeSubscription = this.route.paramMap.subscribe(params => {
      const sku = Number(params.get('sku'));
      this.inventoryService.getProduct(sku).subscribe((product) => {
        this.sku = product.sku;
        this.name = product.name;
        this.summary = product.description.summary
        this.image = (product.images && product.images.length > 0) ? product.images[0] : this.placeholder
        this.stock = product.stockQuantity
        this.setStockStatus()
        this.price = product.price
        this.tags = product.description.tags;
        this.getReviews(sku);
      });
    });
    
  }

  ngOnDestroy(): void {
    if (this.routeSubscription) {
      this.routeSubscription.unsubscribe();
    }
  }


  setStockStatus(): void {
    if (this.stock == this.QUANTITY_OUT_OF_STOCK) {
        this.stockStatus = "Out Of Stock";
    }
    else if (this.stock <= this.QUANTITY_LOW_STOCK) {
        this.stockStatus = "Low Stock";
    }
    else {
        this.stockStatus = "In Stock";
    }
  }

  // add the selected product to the cart
  addToCart(sku: number) {
    this.cartsService.addProductToCart(sku, 1, true).subscribe({
      next: (response) => {
        if (response) {
          console.log('Product added to cart');
        } else {
          console.log('Failed to add product to cart');
        }
      },
      error: (err) => {
        console.error(err);
      },
    });
  }

  // Get all the review for this product
  public getReviews(sku: number) {
    this.reviewService.getReviewsForProduct(sku).subscribe( reviews => {
      this.reviews = reviews;
    });
  }

  // returns star based on the numeric rating value
  getStarArray(rating: number): number[] {
  const starCount = Math.round(rating);
  return Array(starCount).fill(0);
}
}