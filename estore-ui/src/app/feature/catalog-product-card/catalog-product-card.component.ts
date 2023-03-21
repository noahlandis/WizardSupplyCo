import { Component, Input, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Product } from 'src/app/model/product.model';
import { CartsService } from '../../services/carts.service';
import { UsersService } from '../../services/users.service';
@Component({
  selector: 'app-catalog-product-card',
  templateUrl: './catalog-product-card.component.html',
  styleUrls: ['./catalog-product-card.component.css'],
})
export class CatalogProductCardComponent implements OnInit {
  @Input() name = '';
  @Input() price = 0;
  @Input() description = '';
  @Input() image = '';
  @Input() sku = 0;
  userId: any;

  constructor(
    private cartsService: CartsService,
    private usersService: UsersService
  ) {}

  ngOnInit(): void {
    this.getUserId();
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
}
