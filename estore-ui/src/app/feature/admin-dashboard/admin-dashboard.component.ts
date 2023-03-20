import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { InventoryService } from 'src/app/services/inventory.service';
import { Product } from 'src/app/model/product.model';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent {
  products : Product[] = [];


  constructor(private router: Router, private inventoryService: InventoryService) {} 

  ngOnIt(): void {
    this.getProducts();
  }

  getProducts(): void {
    this.inventoryService.getProducts().subscribe(products => { this.products = products });
  }
}
