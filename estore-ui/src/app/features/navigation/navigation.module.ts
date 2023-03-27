import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { RouterModule } from '@angular/router';

import { MaterialModule } from 'src/app/material.module';

import { TopNavComponent } from './top-nav/top-nav.component';
import { ProductSearchComponent } from './product-search/product-search.component';
import { SideNavComponent } from './side-nav/side-nav.component';

@NgModule({
  declarations: [TopNavComponent, SideNavComponent, ProductSearchComponent],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    CommonModule,
    AppRoutingModule,
    RouterModule,
    MaterialModule,
  ],
  exports: [
    TopNavComponent,
    SideNavComponent,
    ProductSearchComponent
  ]
})
export class NavigationModule { }
