import {inject} from '@angular/core';
import { Router, UrlTree } from '@angular/router';
import { Observable, of } from 'rxjs';

import {AuthService} from './services/auth.service';

export const authCustomerGuard = (): Observable<boolean | UrlTree> => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.getIsLoggedIn() && !authService.getIsAdmin()) {
    return of(true);
  }

  // Redirect to the login page
  return of(router.parseUrl('/auth'));
};
