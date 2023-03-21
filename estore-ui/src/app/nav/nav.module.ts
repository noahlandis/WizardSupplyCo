import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from '../app-routing.module';

import { TopNavComponent } from './top-nav/top-nav.component';
import { SideNavComponent } from './side-nav/side-nav.component';

import { MaterialModule } from '../material.module';
import { ProductSearchComponent } from './product-search/product-search.component';

@NgModule({
  declarations: [TopNavComponent, SideNavComponent, ProductSearchComponent],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    CommonModule,
    AppRoutingModule,
    MaterialModule,
  ],
  exports: [
    TopNavComponent,
    SideNavComponent,
    ProductSearchComponent
  ]
})
export class NavModule { }
