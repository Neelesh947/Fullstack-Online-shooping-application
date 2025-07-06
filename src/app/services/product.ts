import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TokenService } from './token-service';
import { Observable } from 'rxjs';

const base_url = 'http://localhost:1236/Shopping';

export interface ProductDto{
  id?: string;
  name: string;
  description: string;
  price: number;
  images?: string[];
  quantity: number;
}

@Injectable({
  providedIn: 'root'
})
export class Product {

  constructor(private http: HttpClient,
    private tokenService: TokenService) { }


  private getAuthHeaders(): HttpHeaders {
    const token = this.tokenService.getToken() || '';
    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  } 
  
  public getListOfProducts(): Observable<ProductDto[]>{
    return this.http.get<ProductDto[]>(`${base_url}/product`, {
      headers: this.getAuthHeaders()});
  }

  public createProduct(productDto: Omit<ProductDto, 'id' | 'images'>, images: File[]): Observable<any> {
    const formData = new FormData();
    const productJson = new Blob([JSON.stringify(productDto)], { type: 'application/json' });
    formData.append('productdto', productJson);
    images.forEach(image => formData.append('images', image));

    return this.http.post(`${base_url}/product`, formData, {
      headers: this.getAuthHeaders() ,
      responseType: 'text'
    });
  }

  public updateProduct(productId: string, productDto: Omit<ProductDto, 'id' | 'images'>, images: File[]): Observable<any> {
    const formData = new FormData();
    const productJson = new Blob([JSON.stringify(productDto)], { type: 'application/json' });
    formData.append('productdto', productJson);
    images.forEach(image => formData.append('images', image));

    return this.http.put(`${base_url}/product/${productId}`, formData, {
      headers: this.getAuthHeaders() ,
      responseType: 'text'
    });
  }

  public deleteProduct(productId: string): Observable<any> {
    return this.http.delete(`${base_url}/product/${productId}`, {
      headers: this.getAuthHeaders(),
      responseType: 'text'
    });
  }

  public getProductById(productId: string): Observable<any>{
    return this.http.get(`${base_url}/product/${productId}`, {
      headers: this.getAuthHeaders(),
      responseType: 'text'
    });
  }

  public getListOfAllProduct() : Observable<ProductDto[]>{
    return this.http.get<ProductDto[]>(`${base_url}/product/all-product`, {
      headers: this.getAuthHeaders()});
  }
}
