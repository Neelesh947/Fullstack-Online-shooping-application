import { Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';
import { BehaviorSubject } from 'rxjs';

export interface DecodedToken {
  sub: string;
  realm_access?: {
    roles: string[];
  };
  authorities?: string[];
  exp: number;
  iat: number;
  [key: string]: any;
}

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  private tokenKey = 'token';

  // Auth state observable
  private authStatus = new BehaviorSubject<boolean>(this.isLoggedIn());
  authStatus$ = this.authStatus.asObservable();

  constructor() {}

  // Save token
  setToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
    this.authStatus.next(true); // Update login status
  }

  // Retrieve token
  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  // Clear token on logout
  clearToken(): void {
    localStorage.removeItem(this.tokenKey);
    this.authStatus.next(false); // Update login status
  }

  // Decode the token
decodeToken(token: string | null): any | null {
  if (!token || typeof token !== 'string') {
    console.error('Invalid token: token is empty or not a string');
    return null;
  }
  // JWT has 3 parts separated by dots
  const parts = token.split('.');
  if (parts.length !== 3) {
    console.error('Invalid token: token does not have 3 parts');
    return null;
  }
  try {
    return jwtDecode(token);
  } catch (error) {
    console.error('Invalid token during decoding:', error);
    return null;
  }
}


  // Get full user details (decoded token)
  getUserDetails(): DecodedToken | null {
    const token = this.getToken();
    return token ? this.decodeToken(token) : null;
  }

  // Get username (subject)
  getUsername(): string {
    return this.getUserDetails()?.sub || '';
  }

  // Get user roles
  getRoles(): string[] {
    const decoded = this.getUserDetails();
    return decoded?.['roles'] || decoded?.authorities || [];
  }

  // Check if token is expired
  isTokenExpired(): boolean {
    const token = this.getToken();
    if (!token) return true;

    const decoded = this.decodeToken(token);
    if (!decoded || !decoded.exp) return true;

    const now = Math.floor(Date.now() / 1000); // current time in seconds
    return now > decoded.exp;
  }

  // Check login status
  isLoggedIn(): boolean {
    const token = this.getToken();
    return !!token && !this.isTokenExpired();
  }

  // Manually update login status
  updateAuthStatus(isLoggedIn: boolean): void {
    this.authStatus.next(isLoggedIn);
  }

  hasRole(role: string): boolean {
    const user = this.getUserDetails();
    if (!user || !user.realm_access || !user.realm_access.roles) return false;
    return user.realm_access.roles.includes(role);
  }
}