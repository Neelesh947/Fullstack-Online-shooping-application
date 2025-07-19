import { Injectable, NgZone } from '@angular/core';
import { Router } from '@angular/router';
import { fromEvent, merge, Subscription } from 'rxjs';
import { debounceTime } from 'rxjs/operators';
import { LoginSerice } from './login-serice';
import { TokenService } from './token-service';

@Injectable({ providedIn: 'root' })
export class UserActivityService {
  private activityEvents = ['click', 'mousemove', 'keydown'];
  private subscription: Subscription = new Subscription();
  private refreshInterval = 60 * 1000; // 1 min

  constructor(
    private loginService: LoginSerice,
    private tokenService: TokenService,
    private zone: NgZone,
    private router: Router
  ) {}

  start(): void {
    this.zone.runOutsideAngular(() => {
      const activity$ = merge(...this.activityEvents.map(evt => fromEvent(document, evt))).pipe(debounceTime(500));

      this.subscription.add(activity$.subscribe(() => {
        const exp = this.tokenService.getUserDetails()?.exp;
        if (exp && Date.now() >= (exp * 1000 - 30000)) {
          const refreshToken = this.tokenService.getRefreshToken();
          if (refreshToken) {
            this.loginService.refreshToken(refreshToken).subscribe({
              next: (res: any) => this.tokenService.setTokens(res.access_token, res.refresh_token),
              error: () => this.logout()
            });
          }
        }
      }));
    });
  }

  stop(): void {
    this.subscription.unsubscribe();
  }

  logout(): void {
    this.tokenService.clearTokens();
    this.router.navigate(['/login']);
  }
}