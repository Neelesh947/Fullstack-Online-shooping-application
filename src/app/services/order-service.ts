import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TokenService } from './token-service';
import { Observable } from 'rxjs';

const base_url = 'http://localhost:1236/Shopping/orders';

export interface OrderItemDto {
  productId: string;
  quantity: number;
}

export interface OrderCreateDto {
  items: OrderItemDto[];
  shippingAddress: string;
}

export interface OrderItemResponseDto {
  productId: string;
  productName: string;
  quantity: number;
  price: number;
}

export interface OrderResponseDto {
  orderId: string;
  customerId: string;
  items: OrderItemResponseDto[];
  shippingAddress: string;
  status: string;
  createdDate: string;
  updatedDate: string;
}

export interface OrderTrackingDto {
  orderId: string;
  status: string;
  estimatedDelivery: string;
}

export interface OrderInvoiceDto {
  orderId: string;
  invoiceData: string; // Base64 or PDF URL depending on backend format
}

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private http: HttpClient,
    private tokenService: TokenService) { }

  private getAuthHeaders(): HttpHeaders {
    const token = this.tokenService.getToken() || '';
    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }  

  createOrder(order: OrderCreateDto): Observable<OrderResponseDto> {
    return this.http.post<OrderResponseDto>(`${base_url}`, order, {
      headers: this.getAuthHeaders()
    });
  }

  getUserOrders(): Observable<OrderResponseDto[]> {
    return this.http.get<OrderResponseDto[]>(`${base_url}`, {
      headers: this.getAuthHeaders()
    });
  }

  getOrderById(orderId: string): Observable<OrderResponseDto> {
    return this.http.get<OrderResponseDto>(`${base_url}/${orderId}`, {
      headers: this.getAuthHeaders()
    });
  }

  trackOrder(orderId: string): Observable<OrderTrackingDto> {
    return this.http.get<OrderTrackingDto>(`${base_url}/${orderId}/track`, {
      headers: this.getAuthHeaders()
    });
  }

  getInvoice(orderId: string): Observable<OrderInvoiceDto> {
    return this.http.get<OrderInvoiceDto>(`${base_url}/${orderId}/invoice`, {
      headers: this.getAuthHeaders()
    });
  }

  cancelOrder(orderId: string): Observable<void> {
    return this.http.delete<void>(`${base_url}/${orderId}`, {
      headers: this.getAuthHeaders()
    });
  }
}
