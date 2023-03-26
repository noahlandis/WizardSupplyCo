import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CatalogProductCardComponent } from './catalog-product-card.component';

describe('CatalogProductCardComponent', () => {
  let component: CatalogProductCardComponent;
  let fixture: ComponentFixture<CatalogProductCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CatalogProductCardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CatalogProductCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
