import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-store-analytics-component',
  standalone: false,
  templateUrl: './store-analytics-component.html',
  styleUrl: './store-analytics-component.css'
})
export class StoreAnalyticsComponent implements OnInit{

  stats = {
    totalSales: 125000,
    totalOrders: 320,
    totalProducts: 85,
    totalCustomers: 240
  };

  topProducts = [
    { name: 'Wireless Headphones', sales: 55000 },
    { name: 'Smart Watch', sales: 32000 },
    { name: 'Bluetooth Speaker', sales: 18000 },
    { name: 'USB-C Charger', sales: 10000 }
  ];

  constructor() {}

  ngOnInit(): void {}
}
