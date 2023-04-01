import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, switchMap, tap } from 'rxjs/operators';

import { Cart } from '../model/cart.model';
import { MessageService } from './message.service';
import { UpdateService } from './update.service';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class CartsService {
private numberOfProductsInCart: BehaviorSubject<number> = new BehaviorSubject<number>(0);  private cartsUrl = 'http://localhost:8080/carts'; // URL to estore-api carts endpoint

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService,
    private updateService: UpdateService,
    private authService: AuthService,
    private router: Router,
    private snackBar: MatSnackBar
  ) { }

  /** Log a CartsService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`CartsService: ${message}`);
  }

  /** GET cart by user id from the server */
  getCart(userId: number): Observable<Cart> {
    const url = `${this.cartsUrl}/${userId}`;
    this.log(`fetching cart w/ userId=${userId} ...`);

    return this.http.get<Cart>(url).pipe(
      tap(_ => this.log(`fetched cart w/ userId=${userId}`)),
      catchError(this.handleError<Cart>(`getCart userId=${userId}`))
    );
  }

  /** PUT: add a quantity of a product to a user's cart on the server */
  addProductToCart(sku: number, quantity: number, triggerUpdate?: boolean): Observable<Cart | null> {
    const userId = this.authService.getUserId();

    // if the user is not logged in, do not add the product to the cart and redirect to login page
    if (!userId) {
      this.log(`user is not logged in, redirecting to login page`);
      this.authService.redirectToLogin();
      return of(null);
    }
    const url = `${this.cartsUrl}/${userId}/products/${sku}?quantity=${quantity}`;
    this.log(`adding product w/ sku=${sku} and quantity=${quantity} to cart w/ userId=${userId} ...`);

    return this.http.put<Cart>(url, this.httpOptions).pipe(
      tap(_ => {
        this.log(`product added to cart successfully`)
        if (triggerUpdate) this.updateService.updateCart();
        this.updateNumberOfProductsInCart(userId);

        // Display snackbar notification
        this.displaySnackBar('Item added to cart', 'Dismiss', 3000, true);
      }),
      catchError((err) => {
        this.handleError<Cart>('addProductToCart');
        this.displaySnackBar('Error adding item to cart', 'Dismiss', 3000, false);
        return of(err);
      })
    );
  }


  /** DELETE: remove a quantity of a product from a user's cart on the server
   * If quantity is not specified, remove all of the product from the cart
   */
  removeProductFromCart(userId: number, sku: number, quantity?: number): Observable<Cart> {
    const url = quantity ? `${this.cartsUrl}/${userId}/products/${sku}/removeQuantity?quantity=${quantity}`
                         : `${this.cartsUrl}/${userId}/products/${sku}`;
    this.log(`removing product w/ sku=${sku} from cart w/ userId=${userId} ...`);

    return this.http.delete<Cart>(url, this.httpOptions).pipe(
      tap(_ => {
        if (quantity) {
          this.log(`successfully removed ${quantity} of product from cart`)
        } else {
          this.log(`successfully removed product from cart`)
          this.updateService.updateCart();
        }
        this.updateNumberOfProductsInCart(userId);
      }),
      catchError(this.handleError<Cart>('removeProductFromCart'))
    );
  }

  /** DELETE: clear cart on the server */
  clearCart(userId: number): Observable<Cart> {
    const url = `${this.cartsUrl}/${userId}/products`;
    this.log(`clearing cart w/ userId=${userId} ...`);

    return this.http.delete<Cart>(url, this.httpOptions).pipe(
      tap(_ => {
        this.log(`cart cleared successfully`)
        this.updateService.updateCart();
        this.updateNumberOfProductsInCart(userId);

      }),
      catchError(this.handleError<Cart>('clearCart'))
    );
  }

  /** Update total product count for the current user cart */
  updateNumberOfProductsInCart(userId: number): void {
    this.getCart(userId).pipe(
      map((cart) => {
        if (cart) {
          let cartMap = cart.productsMap;
          let sum = Object.values(cartMap).reduce((acc, value) => acc + value, 0);
          this.numberOfProductsInCart.next(sum); 
        } else {
          this.numberOfProductsInCart.next(0);
        }
      }),
      catchError((err) => {
        console.error(err);
        return of(0);
      })
    ).subscribe();
  }

  getNumberOfProductsInCart(): Observable<number> {
    return this.numberOfProductsInCart;
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   *
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
  */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /** Display snackbar notification */
  displaySnackBar(message: string, action: string, duration: number, error?: boolean) {
    this.snackBar.open(message, action, {
      duration: duration,
      // panelClass: error ? ['snackbar-error'] : ['snackbar-success']
    });
  }
}
