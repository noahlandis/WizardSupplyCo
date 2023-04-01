import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { InventoryService } from 'src/app/core/services/inventory.service';

@Component({
  selector: 'app-test-product-details',
  templateUrl: './test-product-details.component.html',
  styleUrls: ['./test-product-details.component.css']
})
export class TestProductDetailsComponent implements OnInit, OnDestroy {
  sku = 0;
  private routeSubscription!: Subscription;

  constructor(
    private route: ActivatedRoute,
    private inventoryService: InventoryService
  ) {}

  ngOnInit(): void {
    this.routeSubscription = this.route.paramMap.subscribe(params => {
      const sku = Number(params.get('sku'));
      this.inventoryService.getProduct(sku).subscribe((product) => {
        this.sku = product.sku;
      });
    });
  }

  ngOnDestroy(): void {
    if (this.routeSubscription) {
      this.routeSubscription.unsubscribe();
    }
  }
}
