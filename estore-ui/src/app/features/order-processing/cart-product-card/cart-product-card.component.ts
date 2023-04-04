import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { CartsService } from 'src/app/core/services/carts.service';
import { InventoryService } from 'src/app/core/services/inventory.service';

@Component({
  selector: 'app-cart-product-card',
  templateUrl: './cart-product-card.component.html',
  styleUrls: ['./cart-product-card.component.scss']
})
export class CartProductCardComponent implements OnInit {
  @Input() sku: number = 0;
  @Input() name: string = '';
  @Input() price: number = 0;
  @Input() quantity: number = 0;
  @Input() userId: number = 0;

  @Output() productRemoved = new EventEmitter<void>();
  @Output() quantityChanged = new EventEmitter<void>();

  constructor(private cartsService: CartsService, private router: Router) { }

  ngOnInit(): void {
    
  }
  
  /** Remove product from cart. */
  removeFromCart() {
    console.log('removing product from cart');

    this.cartsService.removeProductFromCart(this.userId, this.sku).subscribe({
      next: (response) => {
        if (response) {
          console.log('Product removed from cart');
        } else {
          console.log('Failed to remove product from cart');
        }
      },
      error: (err) => {
        console.error(err);
      }
    });
  }

  /** Increase quantity by 1. */
  increaseQuantity() {
    console.log('increasing quantity');

    this.cartsService.addProductToCart(this.sku, 1).subscribe({
      next: (response) => {
        if (response) {
          console.log('Product quantity increased');
          this.quantity++;
          this.quantityChanged.emit();
        } else {
          console.log('Failed to increase product quantity');
        }
      },
      error: (err) => {
        console.error(err);
      }
    });
  }

  /** Decrease quantity by 1. Doesn't allow quantities less than 1 */
  decreaseQuantity() {
    console.log('decreasing quantity');

    if (this.quantity > 1) {
      this.cartsService.removeProductFromCart(this.userId, this.sku, 1).subscribe({
        next: (response) => {
          if (response) {
            console.log('Product quantity decreased');
            this.quantity--;
            this.quantityChanged.emit();
          } else {
            console.log('Failed to decrease product quantity');
          }
        },
        error: (err) => {
          console.error(err);
        }
      });
    } else {
      console.log('Quantity cannot be less than 1');
    }
  }
}
