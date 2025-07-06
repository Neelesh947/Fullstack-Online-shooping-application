import { Component, OnInit } from '@angular/core';
import { LoginSerice } from '../../../services/login-serice';
import { TokenService } from '../../../services/token-service';
import { Router } from '@angular/router';
import { SweetAlertService } from '../../../services/sweet-alert-service';
import { Product as ProductService, ProductDto } from '../../../services/product';
import { CartItemRequestDto, CartService } from '../../../services/cart-service';

interface Products {
  id: string;
  name: string;
  description: string;
  price: number;
  imageUrl: string;
  category: string;
  quantity: number;
}

@Component({
  selector: 'app-home-page-component',
  standalone: false,
  templateUrl: './home-page-component.html',
  styleUrl: './home-page-component.css'
})
export class HomePageComponent implements OnInit{

  products: Products[] = [];
  filteredProducts: Products[] = [];
  categories: string[] = [];
  selectedCategory = 'All';
  searchQuery = '';

  constructor(private productSer: ProductService,private authService: LoginSerice,
          private tokenService: TokenService,
          private router: Router,
        private alert: SweetAlertService,
      private cartService: CartService){}

  ngOnInit(): void {
    this.productSer.getListOfAllProduct().subscribe((data: ProductDto[]) => {
      this.products = data.map(dto => ({
        id: dto.id!,
        name: dto.name,
        description: dto.description,
        price: dto.price,
        imageUrl: dto.images && dto.images.length > 0
          ? 'data:image/jpeg;base64,' + dto.images[0]
          : 'assets/placeholder.png',
        category: 'General', 
        quantity: dto.quantity
      }));
      this.filteredProducts = [...this.products]; 
      this.extractCategories(this.products);
    });
  }

  extractCategories(products: Products[]) {
    const categorySet = new Set(products.map(p => p.category));
    this.categories = ['All', ...Array.from(categorySet)];
  }

  filterProducts() {
    this.filteredProducts = this.products.filter(product => {
      const matchesCategory = this.selectedCategory === 'All' || product.category === this.selectedCategory;
      const matchesSearch = product.name.toLowerCase().includes(this.searchQuery.toLowerCase());
      return matchesCategory && matchesSearch;
    });
  }

  onCategoryChange(category: string) {
    this.selectedCategory = category;
    this.filterProducts();
  }

  onSearchChange(query: string) {
    this.searchQuery = query;
    this.filterProducts();
  }

  addToCart(productId: string):void{
    const dto : CartItemRequestDto ={
        productId,
        quantity: 1
    };
    this.cartService.addToCart(dto).subscribe({
      next: () => this.alert.success('Added to cart'),
      error: () => this.alert.error('Failed to add to cart')
    })
  }

}
