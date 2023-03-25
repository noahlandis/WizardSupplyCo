import { Component, Input, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { CartsService } from '../../services/carts.service';
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
    public authService: AuthService
  ) {}

  ngOnInit(): void {
  }

  // add the selected product to the cart
  addToCart(sku: number) {
    console.log(`userId: ${this.userId}, sku: ${sku}`);
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
}
