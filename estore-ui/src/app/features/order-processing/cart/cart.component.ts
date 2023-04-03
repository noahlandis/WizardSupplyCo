  import { Component, OnDestroy, OnInit } from '@angular/core';
  import { CartsService } from 'src/app/core/services/carts.service';
  import { UsersService } from 'src/app/core/services/users.service';
  import { Subscription } from 'rxjs';
  import { UpdateService } from 'src/app/core/services/update.service';
import { CartDetails } from 'src/app/core/model/cart.model';
import { NavigationExtras, Router } from '@angular/router';
  @Component({
    selector: 'app-cart',
    templateUrl: './cart.component.html',
    styleUrls: ['./cart.component.scss']
  })
  export class CartComponent implements OnInit, OnDestroy {
    private cartUpdateSubscription?: Subscription;
    
    userId: number = 0;
    cartDetails: CartDetails | null = null;

    constructor(private usersService: UsersService,
      private cartsService: CartsService,
      private updateService: UpdateService,
      private router: Router
      ) { }

    ngOnInit(): void {
      this.getUserId();
      this.cartsService.getCartDetails(this.userId).subscribe((cartDetails) => {
        this.cartDetails = cartDetails;
      });

      // create an infinite subscription to the cart update observable
      this.cartUpdateSubscription = this.updateService.cartUpdate$.subscribe(() => {
        // Fetch the cart contents when the cart is updated
        this.cartsService.getCartDetails(this.userId).subscribe((cartDetails) => {
          this.cartDetails = cartDetails;
        });
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

    onQuantityChanged() {
      this.cartsService.getCartDetails(this.userId).subscribe((cartDetails) => {
        this.cartDetails = cartDetails;
      });
    }

    onCheckout() {
      const navigationExtras: NavigationExtras = {
        state: { fromCartCheckoutButton: true }
      };
      
      // Navigate to the checkout page and pass the navigation extras
      this.router.navigate(['/checkout'], navigationExtras);
    }
  }
