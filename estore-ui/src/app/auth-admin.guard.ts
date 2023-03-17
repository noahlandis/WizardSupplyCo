import {inject} from '@angular/core';
import { Router } from '@angular/router';

import {AuthService} from './services/auth.service';

export const authAdminGuard = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.getIsLoggedIn() && authService.getIsAdmin()) {
    return true;
  }

  // Redirect to the login page
  return router.parseUrl('/login');
};