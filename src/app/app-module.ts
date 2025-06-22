import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms'; 
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { SignUpComponent } from './component/sign-up-component/sign-up-component';
import { ForgetPasswordComponent } from './component/forget-password-component/forget-password-component';
import { CustomerOrShopkeeperComponent } from './component/customer-or-shopkeeper-component/customer-or-shopkeeper-component';
import { ShopkeeperSignUpComponent } from './component/shopkeeper-sign-up-component/shopkeeper-sign-up-component';
import { HomepageComponent } from './pages/homepage-component/homepage-component';
import { LoginComponent } from './component/login-component/login-component';
import { SuperAdminDashboard } from './pages/superadmin/super-admin-dashboard/super-admin-dashboard';
import { ShopkeeperDashboardComponent } from './pages/shopkeeper/shopkeeper-dashboard-component/shopkeeper-dashboard-component';
import { ProductComponent } from './pages/shopkeeper/product-component/product-component';
import { AddProductComponent } from './pages/shopkeeper/add-product-component/add-product-component';
import { EditProductComponent } from './pages/shopkeeper/edit-product-component/edit-product-component';

@NgModule({
  declarations: [
    App,
    LoginComponent,
    SignUpComponent,
    ForgetPasswordComponent,
    CustomerOrShopkeeperComponent,
    ShopkeeperSignUpComponent,
    HomepageComponent,
    SuperAdminDashboard,
    ShopkeeperDashboardComponent,
    ProductComponent,
    AddProductComponent,
    EditProductComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    provideBrowserGlobalErrorListeners()
  ],
  bootstrap: [App]
})
export class AppModule { }
