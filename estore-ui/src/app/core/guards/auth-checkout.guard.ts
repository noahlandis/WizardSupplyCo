import {inject} from '@angular/core';
import { Router, UrlTree } from '@angular/router';
import { Observable, of } from 'rxjs';

import { AuthService } from '../services/auth.service';
import { CartsService } from '../services/carts.service';
import { MessageService } from '../services/message.service';

export const authCheckoutGuard = (): Observable<boolean | UrlTree> => {
  const authService = inject(AuthService);
  const cartsService = inject(CartsService);
  const messageService = inject(MessageService);
  const router = inject(Router);

  /** Log a authCheckoutGuard message with the MessageService */
  const log = (message: string) => {
    messageService.add(`authCheckoutGuard: ${message}`);
  }

  if (authService.getIsLoggedIn().getValue() && !authService.getIsAdmin().getValue()) {
    const cart = cartsService.getCurrentUserCart();
    if (cart.getValue().items.length > 0) {
      log(`route activated -> user is a customer and has items in their cart`);
      return of(true);
    }

  }

  log(`route denied -> user does not have items in their cart (redirecting to cart page)`);
  // Redirect to the login page
  return of(router.parseUrl('/cart'));
};
