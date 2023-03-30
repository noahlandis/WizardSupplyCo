import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CartsService } from 'src/app/core/services/carts.service';
import { Cart } from 'src/app/core/model/cart.model';
import { BaseOrder, ShippingAddress } from 'src/app/core/model/order.model';
import { OrdersService } from 'src/app/core/services/orders.service';
import { Router } from '@angular/router';
import { CurrencyPipe } from '@angular/common';
import { catchError, Observable, of } from 'rxjs';
import { Product } from 'src/app/core/model/product.model';
import { InventoryService } from 'src/app/core/services/inventory.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.scss'],
  providers: [CurrencyPipe]
})
export class CheckoutComponent implements OnInit {
  isLinear = false;
  contactForm: FormGroup;
  shippingAddressForm: FormGroup;
  paymentForm: FormGroup;
  cart: Cart | null = null;
  orderErrorMessage?: string = '';
  productFetchError: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private cartsService: CartsService,
    private ordersService: OrdersService,
    private inventoryService: InventoryService,
    private router: Router
  ) {
    this.contactForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      phoneNumber: ['', Validators.required],
      emailAddress: ['', [Validators.required, Validators.email]]
    });

    this.shippingAddressForm = this.formBuilder.group({
      country: ['', Validators.required],
      state: ['', Validators.required],
      city: ['', Validators.required],
      zipCode: ['', Validators.required],
      addressLine1: ['', Validators.required],
      addressLine2: [''],
      apartmentNumber: ['']
    });

    this.paymentForm = this.formBuilder.group({
      creditCardNumber: ['', Validators.required],
      nameOnCard: ['', Validators.required],
      expiration: ['', Validators.required],
      cvvCode: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.fetchCart();
  }

  fetchCart(): void {
    this.cartsService.getCurrentUserCart().subscribe(cart => {
      this.cart = cart;
    });
  }

  getProductDetails(sku: number): Observable<Product | null> {
    return this.inventoryService.getProduct(sku).pipe(
      catchError((err) => {
        console.error(err);
        this.productFetchError = true;
        return of(null);
      })
    );
  }

  onSubmit(): void {
    if (!this.cart) {
      return;
    }
  
    const shippingAddress = new ShippingAddress(
      this.shippingAddressForm.value.country,
      this.shippingAddressForm.value.state,
      this.shippingAddressForm.value.city,
      this.shippingAddressForm.value.zipCode,
      this.shippingAddressForm.value.addressLine1,
      this.shippingAddressForm.value.addressLine2,
      this.shippingAddressForm.value.apartmentNumber
    );
  
    const baseOrder = new BaseOrder(
      this.contactForm.value.firstName,
      this.contactForm.value.lastName,
      this.contactForm.value.phoneNumber,
      this.contactForm.value.emailAddress,
      shippingAddress,
      this.cart
    );
  
    // Call placeOrder() from OrdersService
    this.ordersService.placeOrder(baseOrder).subscribe(response => {
      if (response.success) {
        // Navigate to the order confirmation screen with the order ID
        this.router.navigate(['/order-confirmation', response.order?.orderNumber]);
      } else {
        // Show an error message and allow the user to edit their order details
        this.orderErrorMessage = response.errorMessage;
      }
    });
  }
  
}
