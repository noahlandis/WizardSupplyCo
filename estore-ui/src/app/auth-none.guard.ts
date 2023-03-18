import { UrlTree, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from './services/auth.service';
import { map, first } from 'rxjs/operators';
import { Observable } from 'rxjs';

export const noAuthGuard = (): Observable<boolean | UrlTree> => {
  const authService = inject(AuthService);
  const router = inject(Router);

  return authService.getIsLoggedIn().pipe(
    first(),
    map((isLoggedIn) => {
      if (isLoggedIn) {
        // If the user is logged in, navigate to the home page
        return router.parseUrl('/');
      }
      // If the user is not logged in, allow access to the route
      return true;
    })
  );
};
