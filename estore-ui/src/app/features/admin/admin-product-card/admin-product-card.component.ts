import { ComponentType } from '@angular/cdk/portal';
import { Component, Input, TemplateRef } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { InventoryService } from 'src/app/core/services/inventory.service';


@Component({
  selector: 'app-admin-product-card',
  templateUrl: './admin-product-card.component.html',
  styleUrls: ['./admin-product-card.component.scss']
})
export class AdminProductCardComponent {
  @Input() name = '';
  @Input() price = 0;
  @Input() description = '';
  @Input() image = '';
  @Input() sku = 0;
  @Input() stock = 0;

  // string representation of the stock to be displayed to customers
  stockStatus: string = '';
  private readonly QUANTITY_LOW_STOCK = 10;
  private readonly QUANTITY_OUT_OF_STOCK = 0; 

  constructor(
    private inventoryService: InventoryService,
    public dialog: MatDialog
    ) { }

  ngOnInit(): void {
    this.setStockStatus();
  }
  
  // opens the delete product confirmation popup
  openDialog(deleteProductConfirmation: TemplateRef<any>) {
    const dialogRef = this.dialog.open(deleteProductConfirmation, {
      width: '350px'
    });
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


