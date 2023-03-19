import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

import { User } from '../model/user.model';
import { UsersService } from './users.service';
import { MessageService } from './message.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private isLoggedIn = new BehaviorSubject<boolean>(false);
  private isAdmin = new BehaviorSubject<boolean>(false);

  constructor(
    private usersService: UsersService,
    private messageService: MessageService
  ) { this.loadCurrentUserFromLocalStorage(); }

  /** Log an AuthService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`AuthService: ${message}`);
  }

  /** Attempt to register the user with the given username */
  register(username: String): Observable<boolean> {
    return this.usersService.registerUser(username).pipe(
      map((response: any) => {
        // If response has a success property and it is false
        if (response && response.success === false) {
          this.log(response.message);
          return false;
        }
        // If response is a User
        if (response && response.userId) {
          this.log(`registered user w/ userId=${response.userId}, username=${response.username}`);
          // Set the current user in the UsersService
          const user = new User(response.userId, response.username);
          this.usersService.setCurrentUser(user);
          this.saveCurrentUserToLocalStorage(user);
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
    return this.usersService.loginUser(username).pipe(
      map((response: any) => {
        // log all the fields of the response
        this.log(`response: ${JSON.stringify(response)}`);
        // If response has a success property and it is false
        if (response && response.success === false) {
          this.log(response.message);
          return false;
        }
        // If response is a User
        if (response && response.username) {
          this.log(`logged in user w/ userId=${response.userId}, username=${response.username}`);
          // Set the current user in the UsersService
          const user = new User(response.userId, response.username);
          this.usersService.setCurrentUser(user);
          this.saveCurrentUserToLocalStorage(user);
          this.isLoggedIn.next(true);
          this.isAdmin.next(response.username === 'admin');
          return true;
        }
        this.log('response is not a User');
        return false;
      })
    );
  }

  /** Log out the currently logged in user */
  logout(): Observable<boolean> {
    const currentUser = this.usersService.getCurrentUser().getValue();
    if (currentUser) {
      return this.usersService.logoutUser(currentUser.username).pipe(
        map((response: any) => {
          // If response has a success property and it is false
          if (response && response.success === false) {
            this.log(response.message);
            return false;
          }
          // Otherwise, log out the user
          this.log(`logged out user w/ userId=${currentUser.userId}, username=${currentUser.username}`);
          this.usersService.setCurrentUser(null);
          this.saveCurrentUserToLocalStorage(null);
          this.isLoggedIn.next(false);
          this.isAdmin.next(false);
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

  /** Load the current user from local storage */
  loadCurrentUserFromLocalStorage(): void {
    const storedUser = localStorage.getItem('currentUser');
    this.log(`loaded 'currentUser' from localStorage: ${storedUser}`);
    if (storedUser) {
      this.usersService.setCurrentUser(JSON.parse(storedUser));
      this.isLoggedIn.next(true);
      this.isAdmin.next(JSON.parse(storedUser).username === 'admin');
    }
  }

  /** Save the current user to local storage */
  saveCurrentUserToLocalStorage(currentUser: User | null): void {
    if (currentUser) {
      this.log(`setting localStorage key 'currentUser' to ${JSON.stringify(currentUser)}`)
      localStorage.setItem('currentUser', JSON.stringify(currentUser));
    } else {
      this.log(`removing localStorage key 'currentUser'`)
      localStorage.removeItem('currentUser');
    }
  }
}