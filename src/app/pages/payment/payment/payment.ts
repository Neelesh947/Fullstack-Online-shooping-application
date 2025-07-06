import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PaymentService } from '../../../services/payment-service';

declare var Razorpay: any;

interface PaymentMethod {
  mode: string;
  label: string;
  icon: string;
}

@Component({
  selector: 'app-payment',
  standalone: false,
  templateUrl: './payment.html',
  styleUrl: './payment.css'
})
export class Payment implements OnInit{

  @Input() totalPrice!: number; // Total amount in INR
  @Input() customerId!: string;
  @Input() cartId!: string;

  constructor(private paymentService: PaymentService){}

  ngOnInit(): void {

    const state = history.state;

    this.totalPrice = state.totalAmount;
    this.customerId = state.customerId;
    this.cartId = state.cartId;

    if (!this.totalPrice || !this.customerId || !this.cartId) {
      console.warn(
        'PaymentComponent inputs missing. Ensure totalPrice, customerId, and cartId are passed.'
      );
    }
  }

  pay(){
    const payload = {
      customerId: this.customerId,
      cartId: this.cartId,
      amountInPaise: this.totalPrice * 100
    };
    
    this.paymentService.createOrder(payload).subscribe(
      (res:any) =>{
        const order_id = res.razorpayOrderId;
        this.razorPaymethodCalled(res,order_id);
      }
    )
  }

  razorPaymethodCalled(res: any, order_id: any) {
  console.log("debit card called");

  const options = {
    key: res.key,
    amount: this.totalPrice * 100,
    currency: 'INR',
    name: 'Neelesh Shop',
    description: 'Purchase at Neelesh Shop',
    order_id: order_id,

    handler: (response: any) => {
      const verificationData = {
        razorpay_order_id: response.razorpay_order_id,
        razorpay_payment_id: response.razorpay_payment_id,
        razorpay_signature: response.razorpay_signature
      };

      // Call backend verify API
      this.paymentService.verifyPayment(verificationData).subscribe(
        (verifyRes: any) => {
          if (verifyRes.success) {
            alert('✅ Payment successful!');
            // TODO: Navigate to success page, clear cart, etc.
          } else {
            alert('❌ Payment verification failed!');
          }
        },
        err => {
          alert('❌ Error verifying payment');
        }
      );
    },

    prefill: {
      name: 'Neelesh',
      email: 'neelesh@example.com',
      contact: '9876543210'
    },

    theme: {
      color: '#3399cc'
    }
  };

  // ✅ THIS WAS MISSING:
  const rzp = new Razorpay(options);
  rzp.open();
  }

  goBack() {
    history.back();
  }
}
