import { Component, Input, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { CartsService } from '../../services/carts.service';

@Component({
  selector: 'app-catalog-product-card',
  templateUrl: './catalog-product-card.component.html',
  styleUrls: ['./catalog-product-card.component.scss'],
})

export class CatalogProductCardComponent implements OnInit {
  @Input() name = '';
  @Input() price = 0;
  @Input() image = '';
  @Input() sku = 0;
  @Input() stock = 0;
  // string representation of the stock to be displayed to customers
  stockStatus: string = '';
  private readonly QUANTITY_LOW_STOCK = 10;
  private readonly QUANTITY_OUT_OF_STOCK = 0; 
  
  constructor(
    private cartsService: CartsService,
    public authService: AuthService
  ) {}

  ngOnInit(): void {
    this.setStockStatus();
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

  getRandomInt(min: number, max: number) {
    min = Math.ceil(min);
    max = Math.floor(max);
    return Math.floor(Math.random() * (max - min + 1)) + min;
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
