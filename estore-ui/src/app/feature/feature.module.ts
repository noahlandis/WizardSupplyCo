import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../material.module';
import { ReactiveFormsModule } from '@angular/forms';

import { TestCatalogComponent } from './test-catalog/test-catalog.component';
import { LoginRegistrationComponent } from './login-registration/login-registration.component';
import { TestCartComponent } from './test-cart/test-cart.component';
import { TestAdminDashboardComponent } from './test-admin-dashboard/test-admin-dashboard.component';
import { EditProductComponent } from './edit-product/edit-product.component';

@NgModule({
    declarations: [
        TestCatalogComponent,
        LoginRegistrationComponent,
        TestCartComponent,
        TestAdminDashboardComponent,
        EditProductComponent,
    ],
    imports: [
        CommonModule,
        MaterialModule,
        ReactiveFormsModule
    ],
    exports: [
        TestCatalogComponent,
        LoginRegistrationComponent,
        TestCartComponent,
        TestAdminDashboardComponent,
        TestCatalogComponent,
        EditProductComponent,
    ]
})
export class FeatureModule { }