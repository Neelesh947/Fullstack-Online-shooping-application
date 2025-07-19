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

   private accessTokenKey = 'access_token';
  private refreshTokenKey = 'refresh_token';

  // Auth state observable
  private authStatus = new BehaviorSubject<boolean>(this.isLoggedIn());
  authStatus$ = this.authStatus.asObservable();

  constructor() {}

  // Save token
  setTokens(accessToken: string, refreshToken: string): void {
    localStorage.setItem(this.accessTokenKey, accessToken);
    localStorage.setItem(this.refreshTokenKey, refreshToken);
    this.authStatus.next(true);
  }

  // Retrieve token
  getAccessToken(): string | null {
    return localStorage.getItem(this.accessTokenKey);
  }

  getRefreshToken(): string | null {
    return localStorage.getItem(this.refreshTokenKey);
  }

  // Clear token on logout
  clearTokens(): void {
    localStorage.removeItem(this.accessTokenKey);
    localStorage.removeItem(this.refreshTokenKey);
    this.authStatus.next(false);
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
    const token = this.getAccessToken();
    return token ? this.decodeToken(token) : null;
  }

  // Get username (subject)
  getUsername(): string {
    return this.getUserDetails()?.sub || '';
  }

  // Get user roles
  getRoles(): string[] {
    const decoded = this.getUserDetails();
    return decoded?.realm_access?.roles || decoded?.authorities || [];
  }

  // Check if token is expired
  isTokenExpired(): boolean {
    const token = this.getAccessToken();
    if (!token) return true;

    const decoded = this.decodeToken(token);
    if (!decoded || !decoded.exp) return true;

    const now = Math.floor(Date.now() / 1000); // current time in seconds
    return now > decoded.exp;
  }

  // Check login status
  isLoggedIn(): boolean {
    const token = this.getAccessToken();
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