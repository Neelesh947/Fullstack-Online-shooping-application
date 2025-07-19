import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HttpErrorResponse
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, switchMap } from 'rxjs/operators';
import { TokenService } from '../services/token-service';
import { LoginSerice } from '../services/login-serice';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(
    private tokenService: TokenService,
    private loginService: LoginSerice
  ) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const accessToken = this.tokenService.getAccessToken();
    const refreshToken = this.tokenService.getRefreshToken();

    if (accessToken) {
      req = req.clone({ setHeaders: { Authorization: `Bearer ${accessToken}` } });
    }

    return next.handle(req).pipe(
      catchError((error) => {
        if (error instanceof HttpErrorResponse && error.status === 401 && refreshToken) {
          return this.loginService.refreshToken(refreshToken).pipe(
            switchMap((response: any) => {
              this.tokenService.setTokens(response.access_token, response.refresh_token);
              const newReq = req.clone({ setHeaders: { Authorization: `Bearer ${response.access_token}` } });
              return next.handle(newReq);
            })
          );
        }
        return throwError(() => error);
      })
    );
  }
}