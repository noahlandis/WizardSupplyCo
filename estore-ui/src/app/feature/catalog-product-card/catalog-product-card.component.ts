import { Component, Input, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Product } from 'src/app/model/product.model';
import { CartsService } from '../../services/carts.service';
import { UsersService } from '../../services/users.service';

@Component({
  selector: 'app-catalog-product-card',
  templateUrl: './catalog-product-card.component.html',
  styleUrls: ['./catalog-product-card.component.scss'],
})

export class CatalogProductCardComponent implements OnInit {
  @Input() name = '';
  @Input() price = 0;
  @Input() description = '';
  @Input() image = '';
  @Input() sku = 0;
  @Input() stock = 0;
  // string representation of the stock to be displayed to customers
  stockStatus: string = '';
  userId: any;
  private readonly QUANTITY_LOW_STOCK = 10;
  private readonly QUANTITY_OUT_OF_STOCK = 0; 

  constructor(
    private cartsService: CartsService,
    private usersService: UsersService,
  ) {}

  ngOnInit(): void {
    this.getUserId();
    this.setStockStatus();
  }

  getUserId() {
    const currentUser = this.usersService.getCurrentUser().getValue();

    if (currentUser) {
      this.userId = currentUser.userId;
    } else {
      console.error("No user logged in!! This shouldn't happen");
    }
  }

  // add the selected product to the cart
  addToCart(sku: number) {
    console.log(`userId: ${this.userId}, sku: ${sku}`);
    this.cartsService.addProductToCart(this.userId, sku, 1, true).subscribe({
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

  /**
   * Updates the string representation of the stock based on the quantity
   */
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
}
