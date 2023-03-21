import { Component, Input } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { InventoryService } from 'src/app/services/inventory.service';


@Component({
  selector: 'app-admin-product-card',
  templateUrl: './admin-product-card.component.html',
  styleUrls: ['./admin-product-card.component.css']
})
export class AdminProductCardComponent {
  @Input() name = '';
  @Input() price = 0;
  @Input() description = '';
  @Input() image = '';
  @Input() sku = 0;
  deleteForm: any;

  constructor(
    private router: Router,
    private fb: FormBuilder,
    private inventoryService: InventoryService,
    ) {
    }


  onDeleteProduct() {
    // call delete on the inventory service
    this.inventoryService.deleteProduct(this.sku).subscribe({
      next: (deleteSuccess) => {
        if (deleteSuccess) {
          console.log('Deletion successful');
        }
      },
      error: (e) => {
        console.log('Register failed with error:', e);
      }
    });
  }
}


