import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../material.module';
import { ReactiveFormsModule } from '@angular/forms';

import { TestCatalogComponent } from './test-catalog/test-catalog.component';
import { TestCartComponent } from './test-cart/test-cart.component';
import { TestAdminDashboardComponent } from './test-admin-dashboard/test-admin-dashboard.component';
import { LoginRegistrationComponent } from './login-registration/login-registration.component';
import { EditProductComponent } from './edit-product/edit-product.component';
import { CreateProductComponent } from './create-product/create-product.component';
import { CartProductCardComponent } from './cart-product-card/cart-product-card.component';

@NgModule({
    declarations: [TestCartComponent, TestAdminDashboardComponent, TestCatalogComponent, LoginRegistrationComponent, EditProductComponent, CreateProductComponent, CartProductCardComponent, CartComponent, ShoppingCartComponent],
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
        CreateProductComponent
    ]
})
export class FeatureModule { }