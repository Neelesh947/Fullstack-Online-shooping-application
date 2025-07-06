import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-store-profile-component',
  standalone: false,
  templateUrl: './store-profile-component.html',
  styleUrl: './store-profile-component.css'
})
export class StoreProfileComponent {

  store: any = {
    name: 'ElectroMart',
    email: 'store@electromart.com',
    phone: '+91-9876543210',
    address: '123 Main Street, Mumbai, India',
    gstNumber: '27ABCDE1234F1Z5',
    established: '2018-04-15'
  };

  constructor(private router: Router) {}

  ngOnInit(): void {}

  editProfile() {
    this.router.navigate(['/shopkeeper/profile/edit']);
  }

  changePassword() {
    this.router.navigate(['/shopkeeper/profile/change-password']);
  }
}
