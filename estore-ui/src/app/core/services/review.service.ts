import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MessageService } from './message.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { catchError, startWith, switchMap, tap } from 'rxjs/operators';
import { Observable, interval, of } from 'rxjs';
import { BaseReview, Review } from '../model/review.model';
import { UpdateService } from './update.service';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  private reviewsUrl = 'http://localhost:8080/reviews'  // URL to estore-api reviews endpoint

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
  
  constructor(
    private http: HttpClient,
    private messageService: MessageService,
    private snackBar: MatSnackBar,
    private updateService: UpdateService
  ) { }

  /** Log a ReviewService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`ReviewService: ${message}`);
  }

  /** GET review for a particular sku and userId */
  getReview(sku:number, userId:number): Observable<Review> {
    const url = `${this.reviewsUrl}/${sku}/${userId}`;
    this.log('fetching review w/ sku=${sku} and userId=${userId} ...');

    return this.http.get<Review>(url)
      .pipe(
        tap(_ => this.log('fetched review w/ sku=${sku} and userId=${userId}')),
        catchError(this.handleError<Review>('getReview' + sku + userId))
      );
  }

  /** GET reviews by userId */
  getReviewsByUserId(userId:number): Observable<Review[]> {
    const url = `${this.reviewsUrl}/user/${userId}`;
    this.log('fetching reviews w/ userId=${userId} ...');

    return this.http.get<Review[]>(url)
      .pipe(
        tap(_ => this.log('fetched reviews w/ userId=${userId}')),
        catchError(this.handleError<Review[]>('getReviews', []))
      );
  }

  /** GET reviews for the product */
getReviewsForProduct(sku:number): Observable<Review[]> {
    const url = `${this.reviewsUrl}/${sku}`;
    this.log('fetching reviews w/ sku=${sku} ...');

    return this.http.get<Review[]>(url)
      .pipe(
        tap(_ => this.log('fetched reviews w/ sku=${sku}')),
        catchError(this.handleError<Review[]>('getReviews', []))
      );
  }

  /** POST: add a new review to the server */
  createReview(review: BaseReview): Observable<Review> {
    this.log(`creating review: ${JSON.stringify(review)} ...`);
    return this.http.post<Review>(this.reviewsUrl, review, this.httpOptions).pipe(
      tap((newReview: Review) => {
        this.log(`created review: ${JSON.stringify(newReview)}`),
        this.displaySnackBar('Review created successfully', 'Dismiss', 3000, false);
        this.updateService.updateReviews();
      }),
        catchError((err) => {
          this.displaySnackBar('Error creating review', 'OK', 3000, true);
          this.handleError<Review>('createReview');
          return of(err);
        })
    );
}

/** DELETE: delete a review using sku and userId */
deleteReview(sku:number, userId:number): Observable<Review> {
  const url = `${this.reviewsUrl}/${sku}/${userId}`;
  this.log(`deleting review w/ sku=${sku} and userId=${userId} ...`);

  return this.http.delete<Review>(url, this.httpOptions).pipe(
    tap(_ => {
      this.log(`deleted review w/ sku=${sku} and userId=${userId}`),
      this.displaySnackBar('Review deleted successfully', 'Dismiss', 3000, false);
    }),
    catchError((err) => {
      this.displaySnackBar('Error deleting review', 'OK', 3000, true);
      this.handleError<Review>('deleteReview');
      return of(err);
    })
  );
}

/** PUT: update a review on the server */
updateReview(review: Review): Observable<any> {
  this.log(`updating review: ${JSON.stringify(review)} ...`);
  return this.http.put(this.reviewsUrl, review, this.httpOptions).pipe(
    tap(_ => {
      this.log(`updated review: ${JSON.stringify(review)}`),
      this.displaySnackBar('Review updated successfully', 'Dismiss', 3000, false);
    }),
    catchError((err) => {
      this.displaySnackBar('Error updating review', 'OK', 3000, true);
      this.handleError<any>('updateReview');
      return of(err);
    })
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

  /** Display snackbar notification */
  displaySnackBar(message: string, action: string, duration: number, error?: boolean) {
    this.snackBar.open(message, action, {
      duration: duration,
      // panelClass: error ? ['snackbar-error'] : ['snackbar-success']
    });
  }
}
