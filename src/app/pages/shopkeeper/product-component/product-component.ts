import { Component, OnInit } from '@angular/core';
import { Product, ProductDto } from '../../../services/product';
import { LoginSerice } from '../../../services/login-serice';
import { TokenService } from '../../../services/token-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-product-component',
  standalone: false,
  templateUrl: './product-component.html',
  styleUrl: './product-component.css'
})
export class ProductComponent implements OnInit{

  products: ProductDto[] = [];
  loading = false;
  error = '';
  userId: string | undefined;

  currentImageIndex: number[] = [];

  constructor(private product: Product,private authService: LoginSerice,
        private tokenService: TokenService,
        private router: Router){}

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts() : void{
    this.loading = true;
    this.error = '';

    this.product.getListOfProducts().subscribe({
      next: (data: ProductDto[]) =>{
        this.products = data;
        this.currentImageIndex = new Array(this.products.length).fill(0);
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Failed to load products';
        console.error(err);
        this.loading = false;
      }
    })
  }

  selectImage(productIndex: number, imgIndex: number): void {
    this.currentImageIndex[productIndex] = imgIndex;
  }


  logout(): void {
    if (!this.userId) {
      console.error('User ID or Realm missing!');
      return;
    }
    this.authService.logoutUser(this.userId).subscribe({
      next: () => {
        this.tokenService.clearToken();
        this.router.navigate(['']);  
      },
      error: (error) => {
        console.error('Logout failed:', error);
      }
    });
  }

}
