import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { ProductService } from 'src/app/services/product.service';
import { Product } from 'src/app/model/product.model';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.scss']
})
export class EditProductComponent implements OnInit {
  editProductForm: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private productService: ProductService,
    ) {
      this.editProductForm = this.fb.group({
        name: ['', Validators.required],
        // price is required, and must be a decimal number with 2 decimal places
        price: ['', Validators.required, Validators.pattern('[0-9]+(\.[0-9][0-9]?)?')],
        stockQuantity: ['', Validators.required, Validators.pattern('[0-9]*')],
        // images is required, and must be a comma-separated list of URL strings
        images: ['', Validators.pattern('^(https?:\/\/.*\.(?:png|jpg|jpeg|gif|png|svg))(?:, https?:\/\/.*\.(?:png|jpg|jpeg|gif|png|svg))*$')],
        description: ['', Validators.required]
      });
  }

  ngOnInit(): void {
    this.getProduct();
  }

  getProduct(): void {
    const sku = Number(this.route.snapshot.paramMap.get('sku'));
    this.productService.getProduct(sku).subscribe({
      next: product => {
        this.editProductForm.setValue({
          sku: product.sku,
          name: product.name,
          price: product.price,
          stockQuantity: product.stockQuantity,
          images: product.images,
          description: product.description
        });
      },
      error: err => console.log(err)
    });
  }

  /** Product edit form save/submit handler */
  onSubmitEditProductForm(): void {
    if (!this.editProductForm.valid)
      return;
    
    const sku = Number(this.route.snapshot.paramMap.get('sku'));
    const { name, price, stockQuantity, images, description } = this.editProductForm.value;
    const imagesArray: string[] = images.split(', ');

    const product = new Product(sku, name, price, stockQuantity, imagesArray, description);
    console.log('Edit product form submitted with product:', JSON.stringify(product));

    // call update product on the product service
    this.productService.updateProduct(product).subscribe({
      next: (updateSuccess) => {
        if (updateSuccess) {
          console.log('Product updated successfully');
        } else {
          console.log('Product update failed');
        }
      },
      error: (e) => {
        console.log('Product update failed with error:', e);
      }
    });
  }
}
