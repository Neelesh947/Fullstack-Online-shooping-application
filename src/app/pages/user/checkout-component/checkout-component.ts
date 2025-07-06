import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-checkout-component',
  standalone: false,
  templateUrl: './checkout-component.html',
  styleUrl: './checkout-component.css'
})
export class CheckoutComponent {

  checkoutForm: FormGroup;

  constructor(private fb: FormBuilder, private router: Router) {
    this.checkoutForm = this.fb.group({
      fullName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', [Validators.required, Validators.pattern('^[0-9]{10}$')]],
      address: ['', Validators.required],
      city: ['', Validators.required],
      zip: ['', Validators.required],
    });
  }

  onSubmit(): void {
    if (this.checkoutForm.valid) {
      console.log('Shipping Info:', this.checkoutForm.value);
      // Navigate to payment page
      this.router.navigate(['/payment']);
    } else {
      this.checkoutForm.markAllAsTouched();
    }
  }
}
