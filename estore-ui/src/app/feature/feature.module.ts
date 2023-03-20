import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../material.module';
import { ReactiveFormsModule } from '@angular/forms';

import { TestCatalogComponent } from './test-catalog/test-catalog.component';
import { TestCartComponent } from './test-cart/test-cart.component';
import { TestAdminDashboardComponent } from './test-admin-dashboard/test-admin-dashboard.component';
import { LoginRegistrationComponent } from './login-registration/login-registration.component';
import { EditProductComponent } from './edit-product/edit-product.component';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { CreateProductComponent } from './create-product/create-product.component';


@NgModule({
    declarations: [TestCartComponent, TestAdminDashboardComponent, TestCatalogComponent, LoginRegistrationComponent, EditProductComponent, CreateProductComponent, AdminDashboardComponent],
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
        AdminDashboardComponent,
        TestCatalogComponent,
        EditProductComponent,
        CreateProductComponent
    ]
})
export class FeatureModule { }