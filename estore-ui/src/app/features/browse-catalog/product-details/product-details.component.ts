import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { InventoryService } from 'src/app/core/services/inventory.service';
import { Product } from 'src/app/core/model/product.model';
@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.scss']
})

export class ProductDetailsComponent implements OnInit {
  product: Product | undefined;
  name!: string;
  summary!: string;
  image!: string;
  stock = 0
  stockStatus: string | undefined
  price = 0
  sku = 0
  tags: string[] = [ ]
  private readonly QUANTITY_LOW_STOCK = 10;
  private readonly QUANTITY_OUT_OF_STOCK = 0; 
  constructor(
    private route: ActivatedRoute,
    private inventoryService: InventoryService
  ) {}

  ngOnInit(): void {
    const sku = Number(this.route.snapshot.paramMap.get('sku'));
    this.inventoryService.getProduct(sku).subscribe((product) => {
      this.product = product;
      this.name = product.name;
      this.summary = product.description.summary
      this.image = product.images[0]
      this.stock = product.stockQuantity
      this.setStockStatus()
      this.price = product.price
      this.tags = product.description.tags
      this.sku = product.sku
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


