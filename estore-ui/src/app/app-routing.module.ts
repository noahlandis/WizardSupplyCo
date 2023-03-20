import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { authCustomerGuard } from './auth-customer.guard';

import { TestCatalogComponent } from './feature/test-catalog/test-catalog.component';
import { TestCartComponent } from './feature/test-cart/test-cart.component';
import { LoginRegistrationComponent } from './feature/login-registration/login-registration.component';
import { TestAdminDashboardComponent } from './feature/test-admin-dashboard/test-admin-dashboard.component';
import { authAdminGuard } from './auth-admin.guard';
import { noAuthGuard } from './auth-none.guard';
import { EditProductComponent } from './feature/edit-product/edit-product.component';
import { AdminDashboardComponent } from './feature/admin-dashboard/admin-dashboard.component';

const routes: Routes = [
    {path:'', component:TestCatalogComponent},
    {path:'catalog', component:TestCatalogComponent},
    {
        path:'cart',
        component:TestCartComponent,
        canActivate: [authCustomerGuard]
    },
    {
        path:'checkout',
        component:TestCartComponent,
        canActivate: [authCustomerGuard]
    },
    {
        path:'auth',
        component:LoginRegistrationComponent,
        canActivate: [noAuthGuard]
    },
    {
        path:'admin',
        component:TestAdminDashboardComponent,
        canActivate: [authAdminGuard]
    },
    {
        path:'edit-product/:sku',
        component:EditProductComponent,
        canActivate: [authAdminGuard]
    },
    {
        path:'create-product',
        component:TestAdminDashboardComponent,
        canActivate: [authAdminGuard]
    },
    {
        path:'admin-dash',
        component:AdminDashboardComponent,
        canActivate: [noAuthGuard]
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }