import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';

import { Cart } from '../model/cart.model';
import { MessageService } from './message.service';

@Injectable({
  providedIn: 'root'
})
export class CartsService {
  private cartsUrl = 'http://localhost:8080/carts'; // URL to estore-api carts endpoint

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService
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
  addProductToCart(userId: number, sku: number, quantity: number): Observable<Cart> {
    const url = `${this.cartsUrl}/${userId}/products/${sku}?quantity=${quantity}`;
    this.log(`adding product w/ sku=${sku} and quantity=${quantity} to cart w/ userId=${userId} ...`);

    return this.http.put<Cart>(url, this.httpOptions).pipe(
      tap(_ => this.log(`product added to cart successfully`)),
      catchError(this.handleError<Cart>('addProductToCart'))
    );
  }

  /** DELETE: remove a quantity of a product from a user's cart on the server */
  removeProductFromCart(userId: number, sku: number, quantity?: number): Observable<Cart> {
    const url = quantity ? `${this.cartsUrl}/${userId}/products/${sku}/removeQuantity?quantity=${quantity}`
                         : `${this.cartsUrl}/${userId}/products/${sku}`;
    this.log(`removing product w/ sku=${sku} from cart w/ userId=${userId} ...`);

    return this.http.delete<Cart>(url, this.httpOptions).pipe(
      tap(_ => this.log(`product removed from cart successfully`)),
      catchError(this.handleError<Cart>('removeProductFromCart'))
    );
  }

  /** DELETE: clear cart on the server */
  clearCart(userId: number): Observable<Cart> {
    const url = `${this.cartsUrl}/${userId}/products`;
    this.log(`clearing cart w/ userId=${userId} ...`);

    return this.http.delete<Cart>(url, this.httpOptions).pipe(
      tap(_ => this.log(`cart cleared successfully`)),
      catchError(this.handleError<Cart>('clearCart'))
    );
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
}
