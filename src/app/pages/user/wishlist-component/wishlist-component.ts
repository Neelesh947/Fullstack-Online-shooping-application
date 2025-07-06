import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ProductResponseDto, WishlistService } from '../../../services/wishlist-service';
import { CartService } from '../../../services/cart-service';

@Component({
  selector: 'app-wishlist-component',
  standalone: false,
  templateUrl: './wishlist-component.html',
  styleUrl: './wishlist-component.css'
})
export class WishlistComponent {

  wishlistItems: ProductResponseDto[] = [];
  loading: boolean = false;
  error: string | null = null;

  constructor(private router: Router, private wishlistService:WishlistService, private cartService: CartService) {}

  ngOnInit(): void {
    this.loadWishlist();
  }

  loadWishlist():void {
    this.loading = true;
    this.wishlistService.getWishlist().subscribe({
      next: (response ) => {
        this.wishlistItems = response.products;
        this.loading = false;
      },
      error: (err) => {
        console.error('Failed to load wishlist', err);
        this.error = 'Could not load wishlist. Please try again later.';
        this.loading = false;
      }
    })
  }

  viewDetails(productId: string) {
    this.router.navigate(['/product', productId]);
  }

  addToCart(productId: string) {
  const dto = { productId, quantity: 1 };
  this.cartService.addToCart(dto).subscribe({
    next: (response) => {
      console.log('Added to cart:', response);
      // Optionally remove from wishlist or update UI here
    },
    error: (err) => {
      console.error('Failed to add to cart:', err);
    }
  });
}


  removeFromWishlist(productId: string) {
    this.wishlistService.removeFromWishlist(productId).subscribe({
      next: () => {
        this.wishlistItems = this.wishlistItems.filter(item => item.id !== productId);
      },
      error: (err) => {
        console.error('Failed to remove item from wishlist', err);
        alert('Failed to remove item from wishlist');
      }
    });
  }
}
