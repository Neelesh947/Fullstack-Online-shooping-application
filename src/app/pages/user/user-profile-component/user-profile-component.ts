import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-profile-component',
  standalone: false,
  templateUrl: './user-profile-component.html',
  styleUrl: './user-profile-component.css'
})
export class UserProfileComponent {

  user = {
    name: 'John Doe',
    email: 'john.doe@example.com',
    phone: '9876543210'
  };

  constructor(private router: Router) {}

  navigateToEdit() {
    this.router.navigate(['/user/edit-profile']);
  }

  navigateToChangePassword() {
    this.router.navigate(['/user/change-password']);
  }
}
