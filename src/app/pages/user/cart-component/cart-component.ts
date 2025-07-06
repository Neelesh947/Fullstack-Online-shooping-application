import { Component, OnInit } from '@angular/core';
import { CartItemRequestDto, CartItemResponseDto, CartResponseDto, CartService } from '../../../services/cart-service';
import { Location } from '@angular/common';
import { Router } from '@angular/router';

interface CartItem {
  productId: string;
  name: string;
  price: number;
  quantity: number;
  imageUrl: string;
}

@Component({
  selector: 'app-cart-component',
  standalone: false,
  templateUrl: './cart-component.html',
  styleUrl: './cart-component.css'
})
export class CartComponent implements OnInit{

  cartItems: CartItemResponseDto[] = [];
  totalPrice: number = 0;
  cartId: string = '';
  customerId: string = '';

  constructor(private cartService: CartService, private location: Location, private router:Router){}

   ngOnInit(): void {   
    this.calculateTotal();
    this.loadCart();
  }

  loadCart(): void {
    this.cartService.getCart().subscribe({
      next: (response: CartResponseDto) => {
        this.cartItems = response.items;
        this.totalPrice = response.totalCartPrice;
        this.cartId = response.cartId;
        this.customerId = response.customerId;
      },
      error: err => console.error('Failed to load cart', err)
    });
  }

  updateQuantity(item: CartItemResponseDto, change: number): void {
    const updatedQty = item.quantity + change;

    if (updatedQty <= 0) {
      this.removeItem(item.productId);
      return;
    }

    const dto: CartItemRequestDto = {
      productId: item.productId,
      quantity: updatedQty
    };

    this.cartService.updateItem(dto).subscribe({
      next: response => {
        this.cartItems = response.items;
        this.totalPrice = response.totalCartPrice;
      },
      error: err => console.error('Error updating item', err)
    });
  }

  removeItem(productId: string): void {
    this.cartService.removeItem(productId).subscribe({
      next: response => {
        this.cartItems = response.items;
        this.totalPrice = response.totalCartPrice;
      },
      error: err => console.error('Error removing item', err)
    });
  }

  clearCart(): void {
    this.cartService.clearCart().subscribe({
      next: () => {
        this.cartItems = [];
        this.totalPrice = 0;
      },
      error: err => console.error('Error clearing cart', err)
    });
  }

  moveToWishlist(item: CartItemResponseDto): void {
    const dto: CartItemRequestDto = {
      productId: item.productId,
      quantity: item.quantity
    };

    this.cartService.moveToWishlist(dto).subscribe({
      next: () => {
        this.removeItem(item.productId);
      },
      error: err => console.error('Error moving to wishlist', err)
    });
  }

  calculateTotal(): void {
    this.totalPrice = this.cartItems.reduce(
      (sum, item) => sum + item.price * item.quantity,
      0
    );
  }

  goBack(): void {
  this.location.back();
}
  
  checkout(): void {
    const checkoutPayload = {
      cartId: this.cartId,
      customerId: this.customerId,
      totalAmount: this.totalPrice
    };

    this.router.navigate(['/payment'], { state: checkoutPayload });
  }
}
