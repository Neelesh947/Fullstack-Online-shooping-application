import { Component, OnInit } from '@angular/core';

interface SalesData {
  date: string;
  totalSales: number;
  totalOrders: number;
  revenue: number;
}

interface ProductSales {
  productName: string;
  quantitySold: number;
  revenue: number;
}

@Component({
  selector: 'app-sales-report-component',
  standalone: false,
  templateUrl: './sales-report-component.html',
  styleUrl: './sales-report-component.css'
})
export class SalesReportComponent implements OnInit{

  dateRange: string = 'last7days';
  salesData: SalesData[] = [];
  totalSales: number = 0;
  totalOrders: number = 0;
  totalRevenue: number = 0;
  topProducts: ProductSales[] = [];

  ngOnInit(): void {
    this.loadSalesReport();
  }

  loadSalesReport() {
    // Simulate fetching data (replace with real API call)
    const data: SalesData[] = [
      { date: '2025-06-23', totalSales: 15, totalOrders: 12, revenue: 12000 },
      { date: '2025-06-24', totalSales: 20, totalOrders: 18, revenue: 15000 },
      { date: '2025-06-25', totalSales: 25, totalOrders: 22, revenue: 17000 },
      { date: '2025-06-26', totalSales: 18, totalOrders: 16, revenue: 14000 },
      { date: '2025-06-27', totalSales: 30, totalOrders: 28, revenue: 20000 },
      { date: '2025-06-28', totalSales: 22, totalOrders: 20, revenue: 16000 },
      { date: '2025-06-29', totalSales: 27, totalOrders: 24, revenue: 18000 },
    ];

    this.salesData = data;

    this.totalSales = data.reduce((sum, d) => sum + d.totalSales, 0);
    this.totalOrders = data.reduce((sum, d) => sum + d.totalOrders, 0);
    this.totalRevenue = data.reduce((sum, d) => sum + d.revenue, 0);

    this.topProducts = [
      { productName: 'Wireless Earbuds', quantitySold: 150, revenue: 75000 },
      { productName: 'Smart Watch', quantitySold: 120, revenue: 96000 },
      { productName: 'Bluetooth Speaker', quantitySold: 100, revenue: 50000 },
      { productName: 'Phone Case', quantitySold: 90, revenue: 13500 },
    ];
  }

  onDateRangeChange(event: Event) {
    const select = event.target as HTMLSelectElement;
    this.dateRange = select.value;
    // Reload data based on new range (mock for now)
    this.loadSalesReport();
  }
}
