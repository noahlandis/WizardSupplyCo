import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { authCustomerGuard } from './core/guards/auth-customer.guard'; 
import { authAdminGuard } from './core/guards/auth-admin.guard';
import { noAuthGuard } from './core/guards/auth-none.guard';

import { LoginRegistrationComponent } from './features/login-registration/login-registration.component';

import { CatalogComponent } from './features/browse-catalog/catalog/catalog.component';
import { CartComponent } from './features/order-processing/cart/cart.component';

import { AdminDashboardComponent } from './features/admin/admin-dashboard/admin-dashboard.component';
import { EditProductComponent } from './features/admin/edit-product/edit-product.component';
import { CreateProductComponent } from './features/admin/create-product/create-product.component';
import { ProductDetailsComponent } from './features/browse-catalog/product-details/product-details.component';
const routes: Routes = [
    {path:'', component:CatalogComponent},
    {path:'catalog', component:CatalogComponent},
    {path:'catalog/:sku', component:ProductDetailsComponent},
    {
        path:'cart',
        component:CartComponent,
        canActivate: [authCustomerGuard]
    },
    {
        path:'checkout',
        component:CartComponent,
        canActivate: [authCustomerGuard]
    },
    {
        path:'auth',
        component:LoginRegistrationComponent,
        canActivate: [noAuthGuard]
    },
    {
        path:'admin',
        component:AdminDashboardComponent,
        canActivate: [authAdminGuard]
    },
    {
        path:'edit-product/:sku',
        component:EditProductComponent,
        canActivate: [authAdminGuard]
    },
    {
        path:'create-product',
        component:CreateProductComponent,
        canActivate: [authAdminGuard]
    },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
