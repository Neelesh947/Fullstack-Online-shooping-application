import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

interface OrderItem {
  productId: string;
  name: string;
  quantity: number;
  price: number;
  subtotal: number;
}

interface OrderDetails {
  id: string;
  date: Date;
  status: string;
  shippingAddress: string;
  items: OrderItem[];
  totalAmount: number;
}

@Component({
  selector: 'app-order-details-component',
  standalone: false,
  templateUrl: './order-details-component.html',
  styleUrl: './order-details-component.css'
})
export class OrderDetailsComponent implements OnInit{

  orderId!: string;
  order?: OrderDetails;

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.orderId = this.route.snapshot.paramMap.get('id') || '';

    // TODO: Fetch order details by orderId from backend API/service
    // Sample static data for demo:
    this.order = {
      id: this.orderId,
      date: new Date('2025-06-01'),
      status: 'Delivered',
      shippingAddress: `123, Main Street, City, State, 123456`,
      items: [
        {
          productId: 'P1001',
          name: 'Wireless Headphones',
          quantity: 2,
          price: 1500,
          subtotal: 3000,
        },
        {
          productId: 'P1002',
          name: 'Bluetooth Speaker',
          quantity: 1,
          price: 2000,
          subtotal: 2000,
        },
      ],
      totalAmount: 5000,
    };
  }

  reorder() {
    alert('Reorder functionality coming soon!');
  }
}
