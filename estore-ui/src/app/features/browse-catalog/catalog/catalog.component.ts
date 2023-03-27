import { Component } from '@angular/core';
import { InventoryService } from 'src/app/core/services/inventory.service';
import { Product } from 'src/app/core/model/product.model';

@Component({
  selector: 'app-catalog',
  templateUrl: './catalog.component.html',
  styleUrls: ['./catalog.component.css']
})
export class CatalogComponent {
  products : Product[] = [];
  placeholder: string = 'https://i.imgur.com/gJZTkgt.png';

  constructor(private inventoryService: InventoryService) {}

  ngOnInit(): void {
    this.getProducts();
  }

  getProducts(): void {
    this.inventoryService.getProducts().subscribe(products => { 
      this.products = products 
    });
  }
}
