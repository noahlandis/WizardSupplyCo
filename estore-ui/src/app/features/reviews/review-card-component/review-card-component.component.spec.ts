import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewCardComponentComponent } from './review-card-component.component';

describe('ReviewCardComponentComponent', () => {
  let component: ReviewCardComponentComponent;
  let fixture: ComponentFixture<ReviewCardComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReviewCardComponentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReviewCardComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
