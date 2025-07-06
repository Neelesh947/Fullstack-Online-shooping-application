import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-card-payment-component',
  standalone: false,
  templateUrl: './card-payment-component.html',
  styleUrl: './card-payment-component.css'
})
export class CardPaymentComponent implements OnInit{

  cardForm!: FormGroup;
  months = Array.from({ length: 12 }, (_, i) => i + 1);
  years: number[] = [];
  cardBrand: string | null = null;

  otpSent = false;
  otp = '';
  otpVerified = false;
  otpError: string | null = null;
  countdown = 0;
  private countdownInterval: any;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.cardForm = this.fb.group({
      cardNumber: ['', [Validators.required, Validators.pattern(/^[0-9\s]+$/), this.luhnValidator]],
      cardHolder: ['', Validators.required],
      expiryMonth: ['', Validators.required],
      expiryYear: ['', Validators.required],
      cvv: ['', [Validators.required, Validators.pattern(/^\d{3,4}$/)]]
    });

    const currentYear = new Date().getFullYear();
    for(let i = 0; i < 12; i++) {
      this.years.push(currentYear + i);
    }
  }

  onCardNumberInput() {
    let val = this.cardForm.controls['cardNumber'].value || '';
    // Remove non-digit chars and format with spaces every 4 digits
    val = val.replace(/\D/g, '');
    val = val.replace(/(.{4})/g, '$1 ').trim();
    this.cardForm.controls['cardNumber'].setValue(val, { emitEvent: false });
    this.cardBrand = this.detectCardBrand(val.replace(/\s/g, ''));
  }

  detectCardBrand(cardNumber: string): string | null {
    if (/^4/.test(cardNumber)) return 'Visa';
    if (/^5[1-5]/.test(cardNumber)) return 'MasterCard';
    if (/^3[47]/.test(cardNumber)) return 'American Express';
    if (/^6(?:011|5)/.test(cardNumber)) return 'Discover';
    return null;
  }

  luhnValidator(control: any) {
    const val: string = control.value.replace(/\s/g, '');
    if (!val) return null;

    let sum = 0;
    let shouldDouble = false;
    for (let i = val.length - 1; i >= 0; i--) {
      let digit = parseInt(val.charAt(i));
      if (shouldDouble) {
        digit *= 2;
        if (digit > 9) digit -= 9;
      }
      sum += digit;
      shouldDouble = !shouldDouble;
    }
    return sum % 10 === 0 ? null : { invalidLuhn: true };
  }

  get expiryInvalid(): boolean {
    const month = this.cardForm.controls['expiryMonth'].value;
    const year = this.cardForm.controls['expiryYear'].value;
    if (!month || !year) return false;

    const expiry = new Date(+year, +month - 1, 1);
    const now = new Date();
    // Set to last day of the month
    expiry.setMonth(expiry.getMonth() + 1);
    expiry.setDate(0);

    return expiry < now;
  }

  getCardNumberError(): string {
    const control = this.cardForm.controls['cardNumber'];
    if (control.hasError('required')) return 'Card number is required';
    if (control.hasError('pattern')) return 'Card number must contain only digits';
    if (control.hasError('invalidLuhn')) return 'Card number is invalid';
    return '';
  }

  sendOtp() {
    if (this.cardForm.invalid || this.expiryInvalid) return;

    // Simulate sending OTP
    this.otpSent = true;
    this.otpError = null;
    this.otp = '';
    this.otpVerified = false;
    this.startCountdown(300); // 5 minutes countdown
  }

  startCountdown(seconds: number) {
    this.countdown = seconds;
    if (this.countdownInterval) clearInterval(this.countdownInterval);
    this.countdownInterval = setInterval(() => {
      this.countdown--;
      if (this.countdown <= 0) {
        clearInterval(this.countdownInterval);
        this.otpError = 'OTP expired, please resend OTP';
        this.otpSent = false;
      }
    }, 1000);
  }

  onOtpInput() {
    if (this.otp.length === 6) {
      this.otpError = null;
    }
  }

  verifyOtp() {
    if (this.otp === '123456') { // Simulated OTP check
      this.otpVerified = true;
      this.otpError = null;
      clearInterval(this.countdownInterval);
    } else {
      this.otpError = 'Incorrect OTP, please try again';
    }
  }

  resendOtp() {
    this.sendOtp();
  }

  onSubmit() {
    if (!this.otpVerified) {
      this.otpError = 'Please verify OTP before submitting';
      return;
    }

    if (this.cardForm.invalid || this.expiryInvalid) {
      return;
    }

    // Proceed with payment submission logic here
    alert('Payment successful!');
  }
}
