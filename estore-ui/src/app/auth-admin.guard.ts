import {inject} from '@angular/core';
import { Router, UrlTree } from '@angular/router';
import { Observable, of } from 'rxjs';

import {AuthService} from './services/auth.service';
import { MessageService } from './services/message.service';

export const authAdminGuard = (): Observable<boolean | UrlTree> => {
  const authService = inject(AuthService);
  const messageService = inject(MessageService);
  const router = inject(Router);

  /** Log a authAdminGuard message with the MessageService */
  const log = (message: string) => {
    messageService.add(`authAdminGuard: ${message}`);
  }

  if (authService.getIsLoggedIn().getValue() && authService.getIsAdmin().getValue()) {
    log(`route activated -> user is logged in and is admin`);
    return of(true);
  }

  log(`route denied -> user is not logged in or is not admin (redirecting to login page)`);
  // Redirect to the login page
  return of(router.parseUrl('/auth'));
};
