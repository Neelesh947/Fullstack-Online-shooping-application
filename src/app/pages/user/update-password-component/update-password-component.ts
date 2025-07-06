import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-update-password-component',
  standalone: false,
  templateUrl: './update-password-component.html',
  styleUrl: './update-password-component.css'
})
export class UpdatePasswordComponent {

  passwordForm: FormGroup;
  hideOld = true;
  hideNew = true;
  hideConfirm = true;

  constructor(private fb: FormBuilder, private router: Router) {
    this.passwordForm = this.fb.group({
      oldPassword: ['', [Validators.required]],
      newPassword: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required]]
    }, { validators: this.passwordMatchValidator });
  }

  passwordMatchValidator(group: FormGroup) {
    const newPass = group.get('newPassword')?.value;
    const confirmPass = group.get('confirmPassword')?.value;
    return newPass === confirmPass ? null : { mismatch: true };
  }

  onSubmit() {
    if (this.passwordForm.valid) {
      // TODO: Call API to update password
      alert('Password changed successfully!');
      this.router.navigate(['/user/profile']);
    } else {
      this.passwordForm.markAllAsTouched();
    }
  }

  onCancel() {
    this.router.navigate(['/user/profile']);
  }

  get oldPassword() { return this.passwordForm.get('oldPassword'); }
  get newPassword() { return this.passwordForm.get('newPassword'); }
  get confirmPassword() { return this.passwordForm.get('confirmPassword'); }
}
