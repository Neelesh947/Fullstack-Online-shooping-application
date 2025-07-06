import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TokenService } from './token-service';

const base_url = 'http://localhost:1236/Shopping/wishlist';

export interface ProductResponseDto {
  id: string;
  name: string;
  price: number;
  image: string; 
}

export interface WishlistResponseDto {
  wishlistId: string;
  customerId: string;
  products: ProductResponseDto[];
}

@Injectable({
  providedIn: 'root'
})
export class WishlistService {
  base_url: any;
  baseUrl: any;
  realm: any;

  constructor(private http: HttpClient,
    private tokenService: TokenService
  ) { }

    private getAuthHeaders(): HttpHeaders {
    const token = this.tokenService.getToken() || '';
    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }

  getWishlist(): Observable<WishlistResponseDto> {
    return this.http.get<WishlistResponseDto>(`${base_url}`, {
          headers: this.getAuthHeaders()
        });
  }

  removeFromWishlist(productId: string): Observable<void> {
  return this.http.delete<void>(`${base_url}/${productId}`, {
    headers: this.getAuthHeaders()
  });
  }
}
