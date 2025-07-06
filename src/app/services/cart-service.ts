import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TokenService } from './token-service';
import { Observable } from 'rxjs';

const base_url = 'http://localhost:1236/Shopping/cart';

export interface CartItemRequestDto {
  productId: string;
  quantity: number;
}

export interface CartItemResponseDto {
  productId: string;
  productName: string;
  price: number;
  quantity: number;
  totalPrice: number;
  imageId?:number;
}

export interface CartResponseDto {
  cartId: string;
  customerId: string;
  items: CartItemResponseDto[];
  totalCartPrice: number;
}

@Injectable({
  providedIn: 'root'
})
export class CartService {

  constructor(
    private http: HttpClient,
    private tokenService: TokenService
  ) {}

  private getAuthHeaders(): HttpHeaders {
    const token = this.tokenService.getToken() || '';
    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }

  // Get cart
  public getCart(): Observable<CartResponseDto> {
    return this.http.get<CartResponseDto>(`${base_url}`, {
      headers: this.getAuthHeaders()
    });
  }

  // Add item to cart
  public addToCart(dto: CartItemRequestDto): Observable<CartResponseDto> {
    return this.http.post<CartResponseDto>(`${base_url}/add`, dto, {
      headers: this.getAuthHeaders()
    });
  }

  // Update cart item
  public updateItem(dto: CartItemRequestDto): Observable<CartResponseDto> {
    return this.http.put<CartResponseDto>(`${base_url}/update`, dto, {
      headers: this.getAuthHeaders()
    });
  }

  // Remove item from cart
  public removeItem(productId: string): Observable<CartResponseDto> {
    return this.http.delete<CartResponseDto>(`${base_url}/remove/${productId}`, {
      headers: this.getAuthHeaders()
    });
  }

  // Clear entire cart
  public clearCart(): Observable<void> {
    return this.http.delete<void>(`${base_url}/clear`, {
      headers: this.getAuthHeaders()
    });
  }

  // Move item to wishlist
  public moveToWishlist(dto: CartItemRequestDto): Observable<void> {
    return this.http.post<void>(`${base_url}/move-to-wishlist`, dto, {
      headers: this.getAuthHeaders()
    });
  }
}
