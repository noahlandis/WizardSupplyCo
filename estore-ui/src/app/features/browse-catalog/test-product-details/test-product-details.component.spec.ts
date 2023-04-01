import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TestProductDetailsComponent } from './test-product-details.component';

describe('TestProductDetailsComponent', () => {
  let component: TestProductDetailsComponent;
  let fixture: ComponentFixture<TestProductDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TestProductDetailsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TestProductDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
