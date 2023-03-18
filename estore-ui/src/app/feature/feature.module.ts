import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TestCartComponent } from './test-cart/test-cart.component';
import { TestLoginComponent } from './test-login/test-login.component';
import { TestAdminDashboardComponent } from './test-admin-dashboard/test-admin-dashboard.component';
import { TestCatalogComponent } from './test-catalog/test-catalog.component';
import { EditProductComponent } from './edit-product/edit-product.component';

@NgModule({
    declarations: [
        TestCartComponent,
        TestLoginComponent,
        TestAdminDashboardComponent,
        TestCatalogComponent,
        EditProductComponent,
    ],
    imports: [
        CommonModule
    ],
    exports: [
        TestCartComponent,
        TestLoginComponent,
        TestAdminDashboardComponent,
        TestCatalogComponent,
        EditProductComponent,
    ]
})
export class FeatureModule { }