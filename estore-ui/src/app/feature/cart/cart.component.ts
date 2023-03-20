import { Component, OnDestroy, OnInit } from '@angular/core';
import { CartsService } from 'src/app/services/carts.service';
import { UsersService } from 'src/app/services/users.service';
import { Subscription } from 'rxjs';
import { UpdateService } from 'src/app/services/update.service';
import { Router } from '@angular/router';

class CartMapEntry {
  constructor(public sku: number, public quantity: number) {}
}

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit, OnDestroy {
  private cartUpdateSubscription?: Subscription;
  
  userId: number = 1;
  cartEntries: CartMapEntry[] = [];
  cartCount: number = 0;
  totalPrice: number = 0;

  constructor(private usersService: UsersService,
    private cartsService: CartsService,
    private updateService: UpdateService,
    ) { }

  ngOnInit(): void {
    this.getUserId();
    this.getCartEntries();

    // create an infinite subscription to the cart update observable
    this.cartUpdateSubscription = this.updateService.cartUpdate$.subscribe(() => {
      this.getCartEntries(); // Fetch the cart contents when the cart is updated
    });
  }

  ngOnDestroy(): void {
    // Unsubscribe from the cart update observable
    if (this.cartUpdateSubscription) {
      this.cartUpdateSubscription.unsubscribe();
    }
  }

  /** Get the current user's id. */
  getUserId() {
    const currentUser = this.usersService.getCurrentUser().getValue();
    if (currentUser) {
      this.userId = currentUser.userId;
    } else {
      console.error('No user logged in!! This shouldn\'t happen');
    }
  }

  /** Get the cart entries for the current user. */
  getCartEntries() {
    this.cartsService.getCart(this.userId).subscribe({
      next: (cart) => {
        if (cart) {
          const items = Object.entries(cart.productsMap);
          this.cartEntries = items.map(([sku, quantity]) => new CartMapEntry(Number(sku), quantity));
          this.cartCount = Number(cart.count);
          this.totalPrice = Number(cart.totalPrice);
        } else {
          console.log('No cart found');
        }
      },
      error: (err) => {
        console.error(err);
      }
    });
  }
}