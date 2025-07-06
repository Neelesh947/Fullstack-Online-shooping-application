import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-edit-profile-component',
  standalone: false,
  templateUrl: './edit-profile-component.html',
  styleUrl: './edit-profile-component.css'
})
export class EditProfileComponent {

  profileForm: FormGroup;

  constructor(private fb: FormBuilder, private router: Router) {
    // Initialize form with example data, replace with actual user data
    this.profileForm = this.fb.group({
      name: ['John Doe', [Validators.required, Validators.minLength(2)]],
      email: ['john.doe@example.com', [Validators.required, Validators.email]],
      phone: ['9876543210', [Validators.required, Validators.pattern('^[0-9]{10}$')]]
    });
  }

  onSubmit() {
    if (this.profileForm.valid) {
      // TODO: Save updated user info (call API)
      alert('Profile updated successfully!');
      this.router.navigate(['/user/profile']);
    } else {
      this.profileForm.markAllAsTouched();
    }
  }

  onCancel() {
    this.router.navigate(['/user/profile']);
  }

  get name() { return this.profileForm.get('name'); }
  get email() { return this.profileForm.get('email'); }
  get phone() { return this.profileForm.get('phone'); }
}
