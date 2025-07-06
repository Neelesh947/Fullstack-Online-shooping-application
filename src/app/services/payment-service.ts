import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

interface CreatePaymentRequest {
  customerId: string;
  cartId: string;
  amountInPaise: number;
}

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  private base_url = "http://localhost:1237/Shopping";

  constructor(private http: HttpClient) { }

  createOrder(payload: CreatePaymentRequest) {
    return this.http.post(`${this.base_url}/api/v1/payment/create`, payload);
  }

  createQRCode(){

  }

  verifyPayment(verificationData:any){
    return this.http.post(`${this.base_url}/api/v1/payment/verify`,verificationData);
  }
}