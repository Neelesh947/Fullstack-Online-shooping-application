import { HttpClient } from '@angular/common/http';
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

  //generate token
  public generateTokens(loginData:any)
  {
    return this.http.post(`${baseUrl}/${realm}/login`,loginData);
  }

  public logoutUser(userId: string): Observable<any> {
    const url = `${baseUrl}/${realm}/logout/${userId}`;
    return this.http.post(url, {}, { responseType: 'text' });
  }
}
