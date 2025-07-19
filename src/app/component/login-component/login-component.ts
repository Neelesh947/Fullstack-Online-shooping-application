import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TokenService } from '../../services/token-service';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import { LoginSerice } from '../../services/login-serice';

@Component({
  selector: 'app-login-component',
  standalone: false,
  templateUrl: './login-component.html',
  styleUrls: ['./login-component.css']
})
export class LoginComponent implements OnInit {

  loginForm!: FormGroup;
  loading = false;
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private loginService: LoginSerice,
    private tokenService: TokenService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      userName: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.loginForm.invalid) return;

    this.loading = true;
    this.errorMessage = '';

    const loginData = this.loginForm.value;

    this.loginService.generateTokens(loginData).subscribe({
      next: (response: any) => {
        this.loading = false;

        if (response?.access_token && response?.refresh_token) {
          this.tokenService.setTokens(response.access_token, response.refresh_token);

          const decodedToken = this.tokenService.getUserDetails();
          console.log('Decoded Token:', decodedToken);

          if (this.tokenService.hasRole('super_admin')) {
            this.router.navigate(['/super-admin']);
          } else if (this.tokenService.hasRole('store_manager')) {
            this.router.navigate(['/shopkeeper-dashboard']);
          } else if (this.tokenService.hasRole('customer')) {
            this.router.navigate(['/homepage']);
          }
        } else if (response?.error) {
          this.errorMessage = response.error_description || 'Authentication failed';
        } else {
          this.errorMessage = 'Unexpected response from server';
        }
      },
      error: (error) => {
        this.loading = false;
        if (error.status === 401) {
          this.errorMessage = 'Invalid username or password';
        } else {
          this.errorMessage = 'An error occurred. Please try again later';
        }
      }
    });
  }
}
