import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../material.module';
import { ReactiveFormsModule } from '@angular/forms';

import { TestCartComponent } from './test-cart/test-cart.component';
import { TestAdminDashboardComponent } from './test-admin-dashboard/test-admin-dashboard.component';
import { TestCatalogComponent } from './test-catalog/test-catalog.component';
import { LoginRegistrationComponent } from './login-registration/login-registration.component';
import { CreateProductComponent } from './create-product/create-product.component';

@NgModule({
    declarations: [TestCartComponent, TestAdminDashboardComponent, TestCatalogComponent, LoginRegistrationComponent, CreateProductComponent],
    imports: [
        CommonModule,
        MaterialModule,
        ReactiveFormsModule
    ],
    exports: [
        TestCartComponent,
        LoginRegistrationComponent,
        TestAdminDashboardComponent,
        TestCatalogComponent
    ]
})
export class FeatureModule { }