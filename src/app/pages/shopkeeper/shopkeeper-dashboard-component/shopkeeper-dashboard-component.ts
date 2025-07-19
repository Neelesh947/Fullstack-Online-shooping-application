import { Component, OnInit } from '@angular/core';
import { LoginSerice } from '../../../services/login-serice';
import { TokenService } from '../../../services/token-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-shopkeeper-dashboard-component',
  standalone: false,
  templateUrl: './shopkeeper-dashboard-component.html',
  styleUrl: './shopkeeper-dashboard-component.css'
})
export class ShopkeeperDashboardComponent implements OnInit{

  userId: string | undefined;
  
    constructor(
      private authService: LoginSerice,
      private tokenService: TokenService,
      private router: Router
    ) {}

    ngOnInit(): void {
    const userDetails = this.tokenService.getUserDetails();

    if(userDetails){
      this.userId = userDetails.sub;
    }
  }

  logout(): void {
    if (!this.userId) {
      console.error('User ID or Realm missing!');
      return;
    }
    this.authService.logoutUser(this.userId).subscribe({
      next: () => {
        this.tokenService.clearTokens();
        this.router.navigate(['']);  
      },
      error: (error) => {
        console.error('Logout failed:', error);
      }
    });
  }
}
