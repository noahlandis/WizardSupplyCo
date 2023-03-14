import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { TestCatalogComponent } from './feature/test-catalog/test-catalog.component';
import { TestCartComponent } from './feature/test-cart/test-cart.component';
import { TestLoginComponent } from './feature/test-login/test-login.component';
import { TestAdminDashboardComponent } from './feature/test-admin-dashboard/test-admin-dashboard.component';

const routes: Routes = [
    {path:'', component:TestCatalogComponent},
    {path:'catalog', component:TestCatalogComponent},
    {path:'cart', component:TestCartComponent},
    {path:'login', component:TestLoginComponent},
    {path:'admin', component:TestAdminDashboardComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }