import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MaterialModule } from './material.module';
import { AppRoutingModule } from './app-routing.module';
import { NavModule } from './nav/nav.module';
import { FeatureModule } from './feature/feature.module';
import { MessagesComponent } from './messages/messages.component';

@NgModule({
  declarations: [
    AppComponent,
    MessagesComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    NavModule,
    FeatureModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
