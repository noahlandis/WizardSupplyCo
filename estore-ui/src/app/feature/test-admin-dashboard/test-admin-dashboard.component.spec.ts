import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TestAdminDashboardComponent } from './test-admin-dashboard.component';

describe('TestAdminDashboardComponent', () => {
  let component: TestAdminDashboardComponent;
  let fixture: ComponentFixture<TestAdminDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TestAdminDashboardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TestAdminDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
