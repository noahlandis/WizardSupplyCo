import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { BaseOrder, Order } from '../model/order.model';
import { MessageService } from './message.service';

@Injectable({
  providedIn: 'root'
})
export class OrdersService {
  private ordersUrl = 'http://localhost:8080/orders'; // URL to estore-api orders endpoint

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService
  ) { }

  /** Log an OrdersService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`OrdersService: ${message}`);
  }

  /** POST: place a new order on the server */
  placeOrder(order: BaseOrder): Observable<Order> {
    this.log(`placing order...`);

    return this.http.post<Order>(this.ordersUrl, order, this.httpOptions).pipe(
      tap(response => {
        this.log(`order placed successfully with orderId=${response.orderNumber}`);
      }),
      catchError(this.handleError<Order>('placeOrder'))
    );
  }

  /** GET: get all the products purchased by a user */
  getProductsOrderedByUserId(userId: number): Observable<number[]> {
    const url = `${this.ordersUrl}/user/${userId}`;
    this.log(`fetching product skus ordered for userId=${userId}...`);

    return this.http.get<number[]>(url).pipe(
      tap(_ => this.log(`fetched orders for userId=${userId}`)),
      catchError(this.handleError<number[]>('getOrdersByUserId', []))
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
