import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TestCatalogComponent } from './test-catalog.component';

describe('TestCatalogComponent', () => {
  let component: TestCatalogComponent;
  let fixture: ComponentFixture<TestCatalogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TestCatalogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TestCatalogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
