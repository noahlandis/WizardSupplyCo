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

const routes: Routes = [
    {path:'', component:TestCatalogComponent},
    {path:'catalog', component:TestCatalogComponent},
    {
        path:'cart',
        component:TestCartComponent,
        canActivateChild: [authCustomerGuard],
        children: [
            {path:'checkout', component:TestCartComponent}
        ]
    },
    {
        path:'auth',
        component:LoginRegistrationComponent,
        canActivate: [noAuthGuard]
    },
    {
        path:'admin',
        component:TestAdminDashboardComponent,
        canActivateChild: [authAdminGuard]
    },
    {
        path:'edit-product/:sku',
        component:EditProductComponent,
        canActivateChild: [authAdminGuard]
    },
    {
        path:'create-product',
        component:TestAdminDashboardComponent,
        canActivateChild: [authAdminGuard]
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }