import { Component, OnInit } from '@angular/core';

interface Order {
  id: string;
  date: Date;
  status: string;
  totalAmount: number;
}

@Component({
  selector: 'app-order-history-component',
  standalone: false,
  templateUrl: './order-history-component.html',
  styleUrl: './order-history-component.css'
})
export class OrderHistoryComponent implements OnInit{

  orders: Order[] = [];

  constructor() {}

  ngOnInit(): void {
    // Fetch orders from API/service here, sample data for now:
    this.orders = [
      {
        id: 'ORD123456',
        date: new Date('2025-06-01'),
        status: 'Delivered',
        totalAmount: 3500,
      },
      {
        id: 'ORD123457',
        date: new Date('2025-06-10'),
        status: 'Processing',
        totalAmount: 1250,
      },
      {
        id: 'ORD123458',
        date: new Date('2025-06-15'),
        status: 'Cancelled',
        totalAmount: 600,
      },
    ];
  }

  viewOrderDetails(orderId: string) {
    // Navigate to order details page or open modal
    alert(`View details for order: ${orderId}`);
  }
}
