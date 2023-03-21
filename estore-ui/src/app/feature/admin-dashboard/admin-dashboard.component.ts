import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { InventoryService } from 'src/app/services/inventory.service';
import { Product } from 'src/app/model/product.model';
import { UpdateService } from 'src/app/services/update.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {
  private dashboardUpdateSubscription?: Subscription;
  products : Product[] = [];


  constructor(private router: Router, private inventoryService: InventoryService, private updateService: UpdateService) {} 
  
  ngOnInit(): void {
    this.getProducts();
    this.dashboardUpdateSubscription = this.updateService.dashboardUpdate$.subscribe(() => {
      this.getProducts();
    });
  }

  ngOnDestroy(): void {
    if (this.dashboardUpdateSubscription) {
      this.dashboardUpdateSubscription.unsubscribe();
    }
  }

  getProducts(): void {
    this.inventoryService.getProducts().subscribe(products => { this.products = products });
    console.log(JSON.stringify(this.products))
  }
}
