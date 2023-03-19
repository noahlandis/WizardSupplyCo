import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';

import { BaseProduct, Product } from '../model/product.model';
import { MessageService } from './message.service';


@Injectable({
  providedIn: 'root'
})
export class InventoryService {

  private productsUrl = 'http://localhost:8080/inventory'  // URL to estore-api inventory endpoint

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService
  ) { }

  /** Log a InventoryService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`InventoryService: ${message}`);
  }

  /** GET products from the server */
  getProducts(): Observable<Product[]> {
    this.log('fetching products...');
    return this.http.get<Product[]>(this.productsUrl)
      .pipe(
        tap(_ => this.log('fetched products')),
        catchError(this.handleError<Product[]>('getProducts', []))
      );
  }

  /** GET product by id. Will 404 if id not found */
  getProduct(sku: number): Observable<Product> {
    const url = `${this.productsUrl}/${sku}`;
    this.log(`fetching product w/ sku=${sku} ...`);

    return this.http.get<Product>(url).pipe(
      tap(_ => this.log(`fetched product w/ sku=${sku}`)),
      catchError(this.handleError<Product>(`getproduct sku=${sku}`))
    );
  }

  /** PUT: update the product on the server */
  updateProduct(product: Product): Observable<any> {
    this.log(`updating product: ${JSON.stringify(product)} ...`);
    return this.http.put(this.productsUrl, product, this.httpOptions).pipe(
      tap(_ => this.log(`product updated successfully`)),
      catchError(this.handleError<any>('updateProduct'))
    );
  }

  /** POST: add a new product to the server */
  addProduct(product: BaseProduct): Observable<Product> {
    this.log(`adding product: ${JSON.stringify(product)} ...`);
    return this.http.post<Product>(this.productsUrl, product, this.httpOptions).pipe(
      tap((newProduct: Product) => this.log(`product added successfully: ${JSON.stringify(newProduct)}`)),
      catchError(this.handleError<Product>('addProduct'))
    );
  }

  /** DELETE: delete the product from the server */
  deleteProduct(sku: number): Observable<Product> {
    const url = `${this.productsUrl}/${sku}`;
    this.log(`deleting product w/ sku=${sku} ...`);

    return this.http.delete<Product>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted product w/ sku=${sku}`)),
      catchError(this.handleError<Product>('deleteProduct'))
    );
  }

  /* GET products whose name contains search term */
  searchProducts(term: string): Observable<Product[]> {
    this.log(`searching for products matching "${term}" ...`);
    if (!term.trim()) {
      // if not search term, return empty product array.
      this.log(`search term is empty, returning empty array ¯\_(ツ)_/¯`);
      return of([]);
    }
    return this.http.get<Product[]>(`${this.productsUrl}/?name=${term}`).pipe(
      tap(x => x.length ?
        this.log(`found products matching "${term}"`) :
        this.log(`no products matching "${term}"`)),
      catchError(this.handleError<Product[]>('searchProducts', []))
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
