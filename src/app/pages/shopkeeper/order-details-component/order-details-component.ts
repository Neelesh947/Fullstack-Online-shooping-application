import { CommonModule } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';

interface OrderItem {
  productId: string;
  productName: string;
  quantity: number;
  price: number;
}

interface Order {
  id: string;
  customerId: string;
  items: OrderItem[];
  shippingAddress: string;
  status: string;
  createDateTime: Date;
  updateDateTime: Date;
}

@Component({
  selector: 'app-order-details-component',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './order-details-component.html',
  styleUrl: './order-details-component.css'
})
export class OrderDetailsComponent implements OnInit{

  @Input() order!: Order;

  totalAmount: number = 0;

   ngOnInit() {
    if (this.order?.items) {
      this.totalAmount = this.order.items.reduce(
        (sum, item) => sum + item.price * item.quantity, 0
      );
    }
  }
}
