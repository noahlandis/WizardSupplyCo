import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, Subject, of } from 'rxjs';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';
import { MatAutocompleteSelectedEvent, MatAutocompleteTrigger } from '@angular/material/autocomplete';

import { Product } from 'src/app/core/model/product.model';
import { InventoryService } from 'src/app/core/services/inventory.service';
import { CartsService } from 'src/app/core/services/carts.service';
import { AuthService } from 'src/app/core/services/auth.service';

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

  userId?: number;

  constructor(
    private inventoryService: InventoryService,
    private cartsService: CartsService,
    public authService: AuthService,
    private router: Router
    ) {}

  // Push a search term into the observable stream.
  search(term: string): void {
    this.searchTerms.next(term);
  }

  ngOnInit(): void {
    this.resetSearchResults();
  }

  /** Clear the search box when an option is selected. */
  onOptionSelected(event: MatAutocompleteSelectedEvent, searchBox: HTMLInputElement): void {
    searchBox.value = ''; // Clear the search box
    event.option.deselect() // Deselect the option to prevent the checkbox icon from appearing
    if (this.authService.getIsAdmin())
      this.router.navigate([`/edit-product/${event.option.value.sku}`]);
    else
      this.router.navigate([`/catalog/${event.option.value.sku}`]);
      
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

    this.cartsService.addProductToCart(product.sku, 1, true).subscribe({
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
