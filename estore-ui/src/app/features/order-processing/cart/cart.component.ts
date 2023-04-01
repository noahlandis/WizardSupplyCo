  import { Component, OnDestroy, OnInit } from '@angular/core';
  import { CartsService } from 'src/app/core/services/carts.service';
  import { UsersService } from 'src/app/core/services/users.service';
  import { Subscription, forkJoin } from 'rxjs';
  import { UpdateService } from 'src/app/core/services/update.service';
  import { InventoryService } from 'src/app/core/services/inventory.service';
  import { Product } from 'src/app/core/model/product.model';

  class CartMapEntry {
    constructor(public sku: number,
                public name: string,
                public price: number,
                public quantity: number) {}
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
    subtotal: number = 0;
    tax: number = 0;
    shipping: number = 15;
    totalPrice: number = 0;

    constructor(private usersService: UsersService,
      private cartsService: CartsService,
      private inventoryService: InventoryService,
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
            if (items.length === 0) {
              console.log('Cart is empty');
              this.cartEntries = [];
              this.cartCount = 0;
              this.subtotal = 0;
              this.calculateSummary();
              return;
            }
    
            // Fetch all product details in parallel
            const productRequests = items.map(([sku]) => this.inventoryService.getProduct(Number(sku)));
            forkJoin(productRequests).subscribe({
              next: (products) => {
                this.cartEntries = products.map((product, index) => {
                  const [, quantity] = items[index];
                  return new CartMapEntry(product.sku, product.name, product.price, Number(quantity));
                });
    
                this.cartCount = Number(cart.count);
                this.subtotal = Number(cart.totalPrice);
                this.calculateSummary();
              },
              error: (err) => {
                console.error(err);
              }
            });
          } else {
            console.log('No cart found');
          }
        },
        error: (err) => {
          console.error(err);
        }
      });
    }
    

    /** Calculate New York state tax. */
    calculateTax() {
      const newYorkStateTaxRate = 0.08875; // New York state tax rate: 8.875%
      this.tax = this.subtotal * newYorkStateTaxRate;
    }

    /** Calculate total price including tax and shipping. */
    calculateTotalPrice() {
      this.totalPrice = this.subtotal + this.tax + this.shipping;
    }

    /** Calculate summary */
    calculateSummary() {
      this.calculateTax();
      this.calculateTotalPrice();
    }

    onQuantityChanged() {
      this.getCartEntries();
    }
  }
