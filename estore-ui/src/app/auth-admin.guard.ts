import {inject} from '@angular/core';
import { Router } from '@angular/router';

import {AuthService} from './services/auth.service';

export const authAdminGuard = (): Observable<boolean | UrlTree> => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.getIsLoggedIn() && authService.getIsAdmin()) {
    return true;
  }

  // Redirect to the login page
  return router.parseUrl('/auth');
};
