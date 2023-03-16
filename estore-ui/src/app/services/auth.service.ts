import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

import { User } from '../model/user.model';
import { UserService } from './user.service';
import { MessageService } from './message.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private isLoggedIn = new BehaviorSubject<boolean>(false);
  private isAdmin = new BehaviorSubject<boolean>(false);

  constructor(
    private userService: UserService,
    private messageService: MessageService
  ) { }

  /** Log an AuthService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`AuthService: ${message}`);
  }

  /** Attempt to register the user with the given username */
  register(username: String): Observable<boolean> {
    return this.userService.registerUser(username).pipe(
      map((response: any) => {
        // If response has a success property and it is false
        if (response && response.success === false) {
          this.log(response.message);
          return false;
        }
        // If response is a User
        if (response && response.userId) {
          this.log(`registered user w/ userId=${response.userId}`);
          this.isLoggedIn.next(true);
          this.isAdmin.next(response.username === 'admin');
          return true;
        }
        return false;
      })
    );
  }

  /** Attempt to login the user with the given username */
  login(username: String): Observable<boolean> {
    return this.userService.loginUser(username).pipe(
      map((response: any) => {
        // If response has a success property and it is false
        if (response && response.success === false) {
          this.log(response.message);
          return false;
        }
        // If response is a User
        if (response && response.userId) {
          this.log(`logged in user w/ userId=${response.userId}`);
          this.isLoggedIn.next(true);
          this.isAdmin.next(response.username === 'admin');
          return true;
        }
        return false;
      })
    );
  }

  /** Log out the currently logged in user */
  logout(): Observable<boolean> {
    const currentUser = this.userService.getCurrentUser().getValue();
    if (currentUser) {
      return this.userService.logoutUser(currentUser.userId).pipe(
        map((response: any) => {
          // If response has a success property and it is false
          if (response && response.success === false) {
            this.log(response.message);
            return false;
          }
          // Otherwise, log out the user
          this.log(`logged out user w/ userId=${currentUser.userId}`);
          this.isLoggedIn.next(false);
          this.isAdmin.next(false);
          this.userService.setCurrentUser(null);
          return true;
        })
      );
    } else {
      // If there is no current user, return an Observable of false
      return of(false);
    }
  }

  /** Returns true if the user is authenticated as admin, false otherwise */
  getIsAdmin(): BehaviorSubject<boolean> {
    return this.isAdmin;
  }

  /** Returns true if the user is authenticated, false otherwise */
  getIsLoggedIn(): BehaviorSubject<boolean> {
    return this.isLoggedIn;
  }
}