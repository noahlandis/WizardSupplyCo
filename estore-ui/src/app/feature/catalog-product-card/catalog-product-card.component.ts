import { Component, Input } from '@angular/core';
import { Subscription } from 'rxjs';
import { Product } from 'src/app/model/product.model';
@Component({
  selector: 'app-catalog-product-card',
  templateUrl: './catalog-product-card.component.html',
  styleUrls: ['./catalog-product-card.component.css'],
})
export class CatalogProductCardComponent {
  @Input() name = '';
  @Input() price = 0;
  @Input() description = '';
  @Input() image = '';
  @Input() sku = 0;

  constructor() {}
}
