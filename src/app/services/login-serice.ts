import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const baseUrl="http://localhost:1234"
const realm = "Shopping"

@Injectable({
  providedIn: 'root'
})
export class LoginSerice {

  isLoggedIn$: any;
  isAdminLoggedIn$: any;
  isCustomerLoggedIn$: any;

  constructor(private http: HttpClient) { }

   /**
   * Login and generate access/refresh tokens
   * @param loginData { userName: string, password: string }
   */  
  public generateTokens(loginData: any): Observable<any> {
    return this.http.post(`${baseUrl}/${realm}/login`, loginData);
  }

  public logoutUser(userId: string): Observable<any> {
    const url = `${baseUrl}/${realm}/logout/${userId}`;
    return this.http.post(url, {}, { responseType: 'text' });
  }

  /**
   * Call backend to refresh the access token using refresh token
   */
  refreshToken(refreshToken: string): Observable<any> {
    const url = `${baseUrl}/${realm}/refresh`;
    return this.http.post(url, { refreshToken });
  }
}
