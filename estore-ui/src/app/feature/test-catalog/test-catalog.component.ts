import { Component } from '@angular/core';
import { InventoryService } from 'src/app/services/inventory.service';
import { Product } from 'src/app/model/product.model';

@Component({
  selector: 'app-test-catalog',
  templateUrl: './test-catalog.component.html',
  styleUrls: ['./test-catalog.component.css']
})
export class TestCatalogComponent {
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
