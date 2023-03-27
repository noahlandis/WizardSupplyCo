import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { InventoryService } from 'src/app/core/services/inventory.service';
import { Description, Product } from 'src/app/core/model/product.model';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.scss']
})
export class EditProductComponent implements OnInit {
  editProductForm: FormGroup;
  
  @ViewChild('saveButtonText', { static: false }) saveButtonText!: ElementRef;

  isLoading = false;
  isSuccess = false;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private inventoryService: InventoryService,
    ) {
      this.editProductForm = this.fb.group({
        name: ['', [Validators.required]],
        // price is required, and must be a decimal number with 2 decimal places
        price: ['', [Validators.required, Validators.pattern('[0-9]+(\.[0-9][0-9]?)?')]],
        stockQuantity: ['', [Validators.required, Validators.pattern('[0-9]*')]],
        // images is required, and must be a comma-separated list of URL strings
        images: ['', [Validators.pattern('^(https?:\/\/.*\.(?:png|jpg|jpeg|gif|png|svg))(?:, https?:\/\/.*\.(?:png|jpg|jpeg|gif|png|svg))*$')]],
        summary: ['', []],
        // tags is required, and must be a comma-separated list of strings
        tags: ['', [Validators.pattern('^[a-zA-Z0-9]+(?:, [a-zA-Z0-9]+)*$')]],
      });
  }

  ngOnInit(): void {
    this.getProduct();
  }

  getProduct(): void {
    const sku = Number(this.route.snapshot.paramMap.get('sku'));
    this.inventoryService.getProduct(sku).subscribe({
      next: product => {
        console.log('Product retrieved:', JSON.stringify(product));
        this.editProductForm.setValue({
          name: product.name,
          price: product.price,
          stockQuantity: product.stockQuantity,
          // Join the images array into a string or set a default value if null
          images: product.images ? product.images.join(', ') : '',
          summary: product.description ? product.description.summary : '',
          // Join the tags array into a string or set a default value if null
          tags: product.description ? product.description.tags.join(', ') : ''
        });
      },
      error: err => console.log(err)
    });
  }

  /** Product edit form save/submit handler */
  async onSubmitEditProductForm(): Promise<void> {
    if (!this.editProductForm.valid)
      return;
    
    const sku = Number(this.route.snapshot.paramMap.get('sku'));
    const { name, price, stockQuantity, images, summary, tags } = this.editProductForm.value;
    const imagesArray: string[] = images ? images.split(', ') : [];

    const tagsArray: string[] = tags ? tags.split(', ') : [];
    const description = new Description(summary, tagsArray);

    const product = new Product(sku, name, price, stockQuantity, imagesArray, description);
    console.log('Edit product form submitted with product:', JSON.stringify(product));

    // call update product on the inventory service
    this.inventoryService.updateProduct(product).subscribe({
      next: async (updateSuccess) => {
        if (updateSuccess) {
          console.log('Product updated successfully');
        } else {
          console.log('Product update failed');
        }
        this.showSubmitFeedback(updateSuccess);
      },
      error: (e) => {
        console.log('Product update failed with error:', e);
      },
    });
  }

  async showSubmitFeedback(success: boolean): Promise<void> {
    this.saveButtonText.nativeElement.textContent = '';
    this.isLoading = true;
    await new Promise(resolve => setTimeout(resolve, 1000));
    this.isLoading = false;
    this.isSuccess = success;
    await new Promise(resolve => setTimeout(resolve, 1000));
    this.isSuccess = false;
    this.saveButtonText.nativeElement.textContent = 'Save';
  }
}
