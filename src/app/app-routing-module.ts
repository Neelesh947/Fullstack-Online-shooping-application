import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './component/login-component/login-component';
import { CustomerOrShopkeeperComponent } from './component/customer-or-shopkeeper-component/customer-or-shopkeeper-component';
import { SignUpComponent } from './component/sign-up-component/sign-up-component';
import { ForgetPasswordComponent } from './component/forget-password-component/forget-password-component';
import { ShopkeeperSignUpComponent } from './component/shopkeeper-sign-up-component/shopkeeper-sign-up-component';
import { HomepageComponent } from './pages/homepage-component/homepage-component';
import { SuperAdminDashboard } from './pages/superadmin/super-admin-dashboard/super-admin-dashboard';
import { ShopkeeperDashboardComponent } from './pages/shopkeeper/shopkeeper-dashboard-component/shopkeeper-dashboard-component';
import { ProductComponent } from './pages/shopkeeper/product-component/product-component';
import { AddProductComponent } from './pages/shopkeeper/add-product-component/add-product-component';
import { EditProductComponent } from './pages/shopkeeper/edit-product-component/edit-product-component';
import { HomePageComponent } from './pages/user/home-page-component/home-page-component';
import { CartComponent } from './pages/user/cart-component/cart-component';
import { Payment } from './pages/payment/payment/payment';

const routes: Routes = [
  {
    path:"",
    component: LoginComponent,
    pathMatch:"full"

  },
  {
    path:"selection",
    component: CustomerOrShopkeeperComponent,
    pathMatch:"full"

  },
  {
    path:"register",
    component: SignUpComponent,
    pathMatch:"full"

  },
  {
    path:"forgot",
    component: ForgetPasswordComponent,
    pathMatch:"full"

  },
  {
    path:"shopkeeper-login",
    component: ShopkeeperSignUpComponent,
    pathMatch:"full"

  },
  {
    path:"homepage",
    component: HomepageComponent,
    pathMatch:"full"

  },
  {
    path:"super-admin",
    component: SuperAdminDashboard,
    pathMatch:"full"

  },
  {
    path:"shopkeeper-dashboard",
    component: ShopkeeperDashboardComponent,
    pathMatch:"full"

  },
  {
    path:"store-product-dashboard",
    component: ProductComponent,
    pathMatch:"full"

  },
  {
    path:"store-add-product-dashboard",
    component: AddProductComponent,
    pathMatch:"full"

  },
  {
    path:"store-edit-product-dashboard/:productId",
    component: EditProductComponent,
    pathMatch:"full"

  },
  {
    path:"user-product-list",
    component: HomePageComponent,
    pathMatch:"full"

  },
  {
    path:"cart-item",
    component: CartComponent,
    pathMatch:"full"

  },
  {
    path:"payment",
    component: Payment,
    pathMatch:"full"

  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
