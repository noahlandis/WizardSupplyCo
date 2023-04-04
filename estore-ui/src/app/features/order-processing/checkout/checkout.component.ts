import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CartsService } from 'src/app/core/services/carts.service';
import { Cart, CartDetails } from 'src/app/core/model/cart.model';
import { BaseOrder, ShippingAddress } from 'src/app/core/model/order.model';
import { OrdersService } from 'src/app/core/services/orders.service';
import { Router } from '@angular/router';
import { CurrencyPipe } from '@angular/common';
import { UsersService } from 'src/app/core/services/users.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.scss'],
  providers: [CurrencyPipe]
})
export class CheckoutComponent implements OnInit {
  isLinear = true;
  contactForm: FormGroup;
  shippingAddressForm: FormGroup;
  paymentForm: FormGroup;
  cartDetails?: CartDetails | null = null;
  cart?: Cart | null = null;
  userId: number = 0;
  orderErrorMessage?: string = '';
  productFetchError: boolean = false;
  orderConfirmation: { orderNumber: number } | null = null;

  constructor(
    private formBuilder: FormBuilder,
    private cartsService: CartsService,
    private ordersService: OrdersService,
    private usersService: UsersService,
    private router: Router,
  ) {
    this.contactForm = this.formBuilder.group({
      firstName: ['', [Validators.required, Validators.minLength(2)]],
      lastName: ['', [Validators.required, Validators.minLength(2)]],
      phoneNumber: ['', [Validators.required, Validators.maxLength(10), Validators.minLength(10), Validators.pattern('[0-9]*')]],
      emailAddress: ['', [Validators.required, Validators.email]]
    });

    this.shippingAddressForm = this.formBuilder.group({
      country: ['', [Validators.required, Validators.minLength(2)]],
      state: ['', [Validators.required, Validators.minLength(2)]],
      city: ['', [Validators.required, Validators.minLength(2)]],
      zipCode: ['', [Validators.required, Validators.maxLength(5), Validators.minLength(5), Validators.pattern('[0-9]*')]],
      addressLine1: ['', Validators.required],
      addressLine2: [''],
      apartmentNumber: ['']
    });

    this.paymentForm = this.formBuilder.group({
      creditCardNumber: ['', [Validators.required, Validators.maxLength(16), Validators.minLength(16), Validators.pattern('[0-9]*')]],
      nameOnCard: ['', [Validators.required, Validators.minLength(2)]],
      expiration: ['', [Validators.required, Validators.maxLength(5), Validators.minLength(5)]],
      cvvCode: ['', [Validators.required, Validators.maxLength(3), Validators.minLength(3), Validators.pattern('[0-9]*')]]
    });
  }

  ngOnInit(): void {
    this.fetchCart();
    this.getUserId();

    this.cartsService.getCartDetails(this.userId).subscribe(cartDetails => {
      this.cartDetails = cartDetails;
    });
  }

  /** Get the current user's id. */
  // TODO: these methods should use authService's getUserId() method 
  // instead of usersService's getCurrentUser() method.
  // also, this method is duplicated to hell and back and should be refactored
  getUserId() {
    const currentUser = this.usersService.getCurrentUser().getValue();
    if (currentUser) {
      this.userId = currentUser.userId;
    } else {
      console.error('No user logged in!! This shouldn\'t happen');
    }
  }

  fetchCart(): void {
    this.cartsService.getCurrentUserCart().subscribe(cart => {
      this.cart = cart;
    });
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
      if (response) {
        // Show the order confirmation page
        this.orderConfirmation = { orderNumber: response.orderNumber };
        this.cartsService.updateNumberOfProductsInCart(response.cart.userId);
      } else {
        // Show an error message and allow the user to edit their order details
        this.orderErrorMessage = 'There was an error placing your order';
      }
    });
  }
  
}
