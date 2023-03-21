import { Component, OnInit, ViewChild } from '@angular/core';

import { Observable, Subject, of } from 'rxjs';

import {
   debounceTime, distinctUntilChanged, switchMap
 } from 'rxjs/operators';

import { Product } from '../../model/product.model';
import { InventoryService } from 'src/app/services/inventory.service';
import { MatAutocompleteSelectedEvent, MatAutocompleteTrigger } from '@angular/material/autocomplete';
import { CartsService } from 'src/app/services/carts.service';
import { UsersService } from 'src/app/services/users.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-product-search',
  templateUrl: './product-search.component.html',
  styleUrls: [ './product-search.component.scss' ]
})
export class ProductSearchComponent implements OnInit {
  products$!: Observable<Product[]>;
  private searchTerms = new Subject<string>();

  // Get a reference to the autocomplete trigger so we can clear the search box when an option is selected
  @ViewChild(MatAutocompleteTrigger, { static: false }) autocompleteTrigger!: MatAutocompleteTrigger;

  userId: number = 1;

  constructor(
    private inventoryService: InventoryService,
    private cartsService: CartsService,
    private usersService: UsersService,
    private router: Router
    ) {}

  // Push a search term into the observable stream.
  search(term: string): void {
    this.searchTerms.next(term);
  }

  ngOnInit(): void {
    this.getUserId();
    
    this.resetSearchResults();
  }

  /** Get the current user's id. */
  getUserId() {
    const currentUser = this.usersService.getCurrentUser().getValue();

    if (currentUser) {
      this.userId = currentUser.userId;
    } else {
      console.error('No user logged in!! This shouldn\'t happen');
    }
  }

  /** Clear the search box when an option is selected. */
  onOptionSelected(event: MatAutocompleteSelectedEvent, searchBox: HTMLInputElement): void {
    searchBox.value = ''; // Clear the search box
    event.option.deselect() // Deselect the option to prevent the checkbox icon from appearing
    this.router.navigate([`/catalog/${event.option.value.sku}`]); // Navigate to the product page
    this.resetSearchResults(); // Reset the search results
  }

  /** Reset the search results and subscribe them to a new observable */
  resetSearchResults(): void {
    this.products$ = of([]); // Clear the search results
    this.products$ = this.searchTerms.pipe(
      // wait 300ms after each keystroke before considering the term
      debounceTime(300),

      // ignore new term if same as previous term
      distinctUntilChanged(),

      // switch to new search observable each time the term changes
      switchMap((term: string) => this.inventoryService.searchProducts(term)),
    );
  }

  // add the selected product to the cart
  addToCart(product: Product, event: MouseEvent, searchBox: HTMLInputElement) {
    event.stopPropagation(); // Stop event propagation to prevent the underlying option from being selected

    console.log(`userId: ${this.userId}, sku: ${product.sku}`);
    this.cartsService.addProductToCart(this.userId, product.sku, 1, true).subscribe({
      next: (response) => {
        if (response) {
          console.log('Product added to cart');
        } else {
          console.log('Failed to add product to cart');
        }
      },
      error: (err) => {
        console.error(err);
      }
    });

    this.autocompleteTrigger.closePanel(); // Close the autocomplete panel
    searchBox.value = ''; // Clear the search box
    this.resetSearchResults(); // Reset the search results
  }
}
