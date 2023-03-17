import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { TestCatalogComponent } from './feature/test-catalog/test-catalog.component';
import { TestCartComponent } from './feature/test-cart/test-cart.component';
import { LoginRegistrationComponent } from './feature/login-registration/login-registration.component';
import { TestAdminDashboardComponent } from './feature/test-admin-dashboard/test-admin-dashboard.component';

const routes: Routes = [
    {path:'', component:TestCatalogComponent},
    {path:'catalog', component:TestCatalogComponent},
    {path:'cart', component:TestCartComponent},
    {path:'auth', component:LoginRegistrationComponent},
    {path:'admin', component:TestAdminDashboardComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }