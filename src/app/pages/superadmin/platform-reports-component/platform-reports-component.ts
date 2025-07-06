import { Component } from '@angular/core';

interface ReportSummary {
  label: string;
  value: number | string;
  icon: string; // emoji or icon class for demo
  bgColor: string;
}

@Component({
  selector: 'app-platform-reports-component',
  standalone: false,
  templateUrl: './platform-reports-component.html',
  styleUrl: './platform-reports-component.css'
})
export class PlatformReportsComponent {

  reports: ReportSummary[] = [];

  salesOverTime: { month: string; sales: number }[] = [];

  ngOnInit() {
    // Dummy data
    this.reports = [
      { label: 'Total Users', value: 15234, icon: '👥', bgColor: '#4caf50' },
      { label: 'Total Stores', value: 312, icon: '🏪', bgColor: '#2196f3' },
      { label: 'Total Orders', value: 12578, icon: '📦', bgColor: '#ff9800' },
      { label: 'Revenue', value: '₹ 98,50,000', icon: '💰', bgColor: '#9c27b0' },
    ];

    this.salesOverTime = [
      { month: 'Jan', sales: 100000 },
      { month: 'Feb', sales: 120000 },
      { month: 'Mar', sales: 90000 },
      { month: 'Apr', sales: 110000 },
      { month: 'May', sales: 150000 },
      { month: 'Jun', sales: 170000 },
      { month: 'Jul', sales: 130000 },
      { month: 'Aug', sales: 160000 },
    ];
  }
}
