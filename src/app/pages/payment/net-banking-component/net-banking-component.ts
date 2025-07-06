import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-net-banking-component',
  standalone: false,
  templateUrl: './net-banking-component.html',
  styleUrl: './net-banking-component.css'
})
export class NetBankingComponent implements OnInit{

  netBankingForm!: FormGroup;
  banks = [
    { value: 'hdfc', display: 'HDFC Bank' },
    { value: 'icici', display: 'ICICI Bank' },
    { value: 'sbi', display: 'State Bank of India' },
    { value: 'axis', display: 'Axis Bank' },
    { value: 'kotak', display: 'Kotak Mahindra Bank' },
  ];

  submitted = false;
  paymentDone = false;
  paymentStatus: 'success' | 'failure' | null = null;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.netBankingForm = this.fb.group({
      bank: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.netBankingForm.invalid) return;

    this.submitted = true;

    // Simulate redirecting to bank portal and response after 5 seconds
    setTimeout(() => {
      // Random payment success or failure simulation
      this.paymentDone = true;
      this.paymentStatus = Math.random() > 0.3 ? 'success' : 'failure';
    }, 5000);
  }

  reset() {
    this.submitted = false;
    this.paymentDone = false;
    this.paymentStatus = null;
    this.netBankingForm.reset();
  }
}
