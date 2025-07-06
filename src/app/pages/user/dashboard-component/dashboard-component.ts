import { Component } from '@angular/core';

@Component({
  selector: 'app-dashboard-component',
  standalone: false,
  templateUrl: './dashboard-component.html',
  styleUrl: './dashboard-component.css'
})
export class DashboardComponent {

  userName = 'Neelesh';
  recentOrders = [
    { id: 'ORD12345', date: '2025-06-25', status: 'Delivered', total: 2599 },
    { id: 'ORD12346', date: '2025-06-20', status: 'Shipped', total: 4999 },
    { id: 'ORD12347', date: '2025-06-18', status: 'Processing', total: 1299 }
  ];

  notifications = [
    { message: 'Your order ORD12346 has shipped!', date: '2 hours ago' },
    { message: 'New discounts on electronics!', date: '1 day ago' }
  ];
}
