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
import { Payment } from './pages/payment/payment/payment';
import { CardPaymentComponent } from './pages/payment/card-payment-component/card-payment-component';
import { UpiPaymentComponent } from './pages/payment/upi-payment-component/upi-payment-component';
import { WalletPaymentComponent } from './pages/payment/wallet-payment-component/wallet-payment-component';
import { NetBankingComponent } from './pages/payment/net-banking-component/net-banking-component';
import { HomePageComponent } from './pages/user/home-page-component/home-page-component';
import { DashboardComponent } from './pages/user/dashboard-component/dashboard-component';
import { ProductDetailsComponent } from './pages/user/product-details-component/product-details-component';
import { CartComponent } from './pages/user/cart-component/cart-component';
import { CheckoutComponent } from './pages/user/checkout-component/checkout-component';
import { WishlistComponent } from './pages/user/wishlist-component/wishlist-component';
import { OrderHistoryComponent } from './pages/user/order-history-component/order-history-component';
import { OrderDetailsComponent } from './pages/user/order-details-component/order-details-component';
import { UserProfileComponent } from './pages/user/user-profile-component/user-profile-component';
import { EditProfileComponent } from './pages/user/edit-profile-component/edit-profile-component';
import { UpdatePasswordComponent } from './pages/user/update-password-component/update-password-component';
import { InventoryManagementComponent } from './pages/shopkeeper/inventory-management-component/inventory-management-component';
import { OrderManagementComponent } from './pages/shopkeeper/order-management-component/order-management-component';
import { CustomerManagementComponent } from './pages/shopkeeper/customer-management-component/customer-management-component';
import { SalesReportComponent } from './pages/shopkeeper/sales-report-component/sales-report-component';
import { StoreProfileComponent } from './pages/shopkeeper/store-profile-component/store-profile-component';
import { StoreAnalyticsComponent } from './pages/shopkeeper/store-analytics-component/store-analytics-component';
import { ManageUsersComponent } from './pages/superadmin/manage-users-component/manage-users-component';
import { ManageStoresComponent } from './pages/superadmin/manage-stores-component/manage-stores-component';
import { PlatformReportsComponent } from './pages/superadmin/platform-reports-component/platform-reports-component';
import { AdminRolesComponent } from './pages/superadmin/admin-roles-component/admin-roles-component';

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
    EditProductComponent,
    Payment,
    CardPaymentComponent,
    UpiPaymentComponent,
    WalletPaymentComponent,
    NetBankingComponent,
    HomePageComponent,
    DashboardComponent,
    ProductDetailsComponent,
    CartComponent,
    CheckoutComponent,
    WishlistComponent,
    OrderHistoryComponent,
    OrderDetailsComponent,
    UserProfileComponent,
    EditProfileComponent,
    UpdatePasswordComponent,
    InventoryManagementComponent,
    OrderManagementComponent,
    CustomerManagementComponent,
    SalesReportComponent,
    StoreProfileComponent,
    StoreAnalyticsComponent,
    ManageUsersComponent,
    ManageStoresComponent,
    PlatformReportsComponent,
    AdminRolesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule,
  ],
  providers: [
    provideBrowserGlobalErrorListeners()
  ],
  bootstrap: [App]
})
export class AppModule { }
