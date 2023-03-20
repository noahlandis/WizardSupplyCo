import { Component, Input } from '@angular/core';
import { Subscription } from 'rxjs';
@Component({
  selector: 'app-catalog-product-card',
  templateUrl: './catalog-product-card.component.html',
  styleUrls: ['./catalog-product-card.component.css'],
})
export class CatalogProductCardComponent {
  @Input() name = '';
  @Input() price = '';
  @Input() description = '';
  @Input() image = '';

  constructor() {}
}
