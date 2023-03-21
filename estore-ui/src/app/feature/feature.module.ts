import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from '../app-routing.module';
import { RouterModule } from '@angular/router';

import { TestCatalogComponent } from './test-catalog/test-catalog.component';
import { CatalogProductCardComponent } from './catalog-product-card/catalog-product-card.component';

import { LoginRegistrationComponent } from './login-registration/login-registration.component';

import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { AdminProductCardComponent } from './admin-product-card/admin-product-card.component';
import { EditProductComponent } from './edit-product/edit-product.component';
import { CreateProductComponent } from './create-product/create-product.component';

import { CartComponent } from './cart/cart.component';
import { CartProductCardComponent } from './cart-product-card/cart-product-card.component';




@NgModule({
    declarations: [TestCatalogComponent, LoginRegistrationComponent, EditProductComponent, CreateProductComponent, CartProductCardComponent, CartComponent, AdminDashboardComponent, AdminProductCardComponent, CatalogProductCardComponent],
    imports: [
        CommonModule,
        MaterialModule,
        ReactiveFormsModule,
        AppRoutingModule,
        RouterModule,
        ReactiveFormsModule,
        AppRoutingModule,
        RouterModule
    ],
    exports: [
        TestCatalogComponent,
        LoginRegistrationComponent,
        AdminDashboardComponent,
        AdminProductCardComponent,
        TestCatalogComponent,
        EditProductComponent,
        CreateProductComponent,
        CatalogProductCardComponent
    ]
})
export class FeatureModule { }
