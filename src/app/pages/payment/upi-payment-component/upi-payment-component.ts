import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-upi-payment-component',
  standalone: false,
  templateUrl: './upi-payment-component.html',
  styleUrl: './upi-payment-component.css'
})
export class UpiPaymentComponent implements OnInit{

  upiForm!: FormGroup;
  loading = false;
  success = false;
  error: string | null = null;

  // Example QR code URL placeholder (can be replaced with dynamic QR generation)
  qrCodeUrl = 'https://api.qrserver.com/v1/create-qr-code/?size=180x180&data=upi://pay?pa=example@bank&pn=ExamplePayee';

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.upiForm = this.fb.group({
      upiId: ['', [Validators.required, Validators.pattern(/^[\w.-]+@[\w.-]+$/)]]
    });
  }

  getUpiIdError(): string {
    const control = this.upiForm.controls['upiId'];
    if (control.hasError('required')) return 'UPI ID is required';
    if (control.hasError('pattern')) return 'Invalid UPI ID format (e.g., abc@okaxis)';
    return '';
  }

  onSubmit() {
    if (this.upiForm.invalid) return;

    this.loading = true;
    this.error = null;
    this.success = false;

    // Simulate async UPI payment approval
    setTimeout(() => {
      this.loading = false;

      // Simulated success/failure randomly
      if (Math.random() > 0.3) {
        this.success = true;
      } else {
        this.error = "Didn't get approval? Please check UPI ID and try again.";
      }
    }, 4000);
  }

  retry() {
    this.error = null;
    this.success = false;
    this.upiForm.reset();
  }
}
