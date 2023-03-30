import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { BaseOrder, Order } from '../model/order.model';
import { MessageService } from './message.service';

interface OrderResponse { success: boolean; order?: Order; errorMessage?: string }

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
  placeOrder(order: BaseOrder): Observable<OrderResponse> {
    this.log(`placing order...`);

    return this.http.post<OrderResponse>(this.ordersUrl, order, this.httpOptions).pipe(
      tap(response => {
        if (response.success) {
          this.log(`order placed successfully with orderId=${response.order?.orderNumber}`);
        } else {
          this.log(`order placement failed: ${response.errorMessage}`);
        }
      }),
      catchError(this.handleError<OrderResponse>('placeOrder'))
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
