import { UrlTree, Router } from '@angular/router';
import { inject } from '@angular/core';
import { map, first } from 'rxjs/operators';
import { Observable } from 'rxjs';

import { AuthService } from '../services/auth.service';
import { MessageService } from '../services/message.service';

export const noAuthGuard = (): Observable<boolean | UrlTree> => {
  const authService = inject(AuthService);
  const messageService = inject(MessageService);
  const router = inject(Router);

  /** Log a noAuthGuard message with the MessageService */
  const log = (message: string) => {
    messageService.add(`noAuthGuard: ${message}`);
  }

  return authService.getIsLoggedIn().pipe(
    first(),
    map((isLoggedIn) => {
      if (isLoggedIn) {
        log(`route denied -> user is logged in (redirecting to home page)`);
        // If the user is logged in, navigate to the home page
        return router.parseUrl('/');
      }
      log(`route activated -> user is not logged in`);
      // If the user is not logged in, allow access to the route
      return true;
    })
  );
};
