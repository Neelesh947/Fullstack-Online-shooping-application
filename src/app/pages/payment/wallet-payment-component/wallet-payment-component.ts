import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-wallet-payment-component',
  standalone: false,
  templateUrl: './wallet-payment-component.html',
  styleUrl: './wallet-payment-component.css'
})
export class WalletPaymentComponent implements OnInit{

  walletForm!: FormGroup;
  wallets = [
    { value: 'paytm', display: 'Paytm' },
    { value: 'phonepe', display: 'PhonePe' },
    { value: 'mobikwik', display: 'Mobikwik' },
  ];

  status: 'idle' | 'pending' | 'success' | 'failure' = 'idle';

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.walletForm = this.fb.group({
      wallet: ['', Validators.required],
      contact: ['', [Validators.required, this.mobileOrEmailValidator]],
    });
  }

  mobileOrEmailValidator(control: any) {
    const value = control.value || '';
    const mobileRegex = /^[6-9]\d{9}$/;
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (mobileRegex.test(value) || emailRegex.test(value)) {
      return null;
    }
    return { invalidContact: true };
  }

  getContactError(): string {
    const control = this.walletForm.controls['contact'];
    if (control.hasError('required')) return 'Mobile or Email is required';
    if (control.hasError('invalidContact')) return 'Enter valid mobile number or email';
    return '';
  }

  onSubmit() {
    if (this.walletForm.invalid) return;

    this.status = 'pending';

    // Simulate async wallet payment process (e.g., redirect or popup)
    setTimeout(() => {
      // Random success or failure simulation
      if (Math.random() > 0.3) {
        this.status = 'success';
      } else {
        this.status = 'failure';
      }
    }, 4000);
  }

  retry() {
    this.status = 'idle';
    this.walletForm.reset();
  }
}
