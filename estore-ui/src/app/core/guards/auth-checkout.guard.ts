import { inject } from '@angular/core';
import { Router, UrlTree } from '@angular/router';
import { Observable, of } from 'rxjs';

import { AuthService } from '../services/auth.service';
import { MessageService } from '../services/message.service';

export const authCheckoutGuard = (): Observable<boolean | UrlTree> => {
  const authService = inject(AuthService);
  const messageService = inject(MessageService);
  const router = inject(Router);

  /** Log an authCheckoutGuard message with the MessageService */
  const log = (message: string) => {
    messageService.add(`authCheckoutGuard: ${message}`);
  };

  if (authService.getIsLoggedIn().getValue() && !authService.getIsAdmin().getValue()) {
    const navigationState = router.getCurrentNavigation()?.extras.state;
    if (navigationState && navigationState['fromCartCheckoutButton']) {
      log(`route activated -> user is logged in, is not an admin, and navigation originated from cart checkout button`);
      return of(true);
    }
  }

  log(`route denied -> user is not logged in, is an admin, or navigation did not originate from cart checkout button (redirecting to cart page)`);
  // Redirect to the cart page
  return of(router.parseUrl('/cart'));
};
