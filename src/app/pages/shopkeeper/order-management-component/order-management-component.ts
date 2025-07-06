import { Component, OnInit } from '@angular/core';


interface Order {
  id: string;
  customerName: string;
  orderDate: Date;
  status: 'Pending' | 'Processing' | 'Shipped' | 'Delivered' | 'Cancelled';
  totalAmount: number;
}

@Component({
  selector: 'app-order-management-component',
  standalone: false,
  templateUrl: './order-management-component.html',
  styleUrl: './order-management-component.css'
})
export class OrderManagementComponent implements OnInit{

  orders: Order[] = [];
  filteredOrders: Order[] = [];
  searchTerm: string = '';
  filterStatus: string = 'All';

  ngOnInit(): void {
    // Sample data — replace with API call
    this.orders = [
      { id: 'ORD001', customerName: 'Neelesh Kumar', orderDate: new Date('2025-06-20'), status: 'Pending', totalAmount: 2599 },
      { id: 'ORD002', customerName: 'Asha Singh', orderDate: new Date('2025-06-18'), status: 'Processing', totalAmount: 4999 },
      { id: 'ORD003', customerName: 'Rahul Jain', orderDate: new Date('2025-06-15'), status: 'Shipped', totalAmount: 1299 },
      { id: 'ORD004', customerName: 'Priya Verma', orderDate: new Date('2025-06-10'), status: 'Delivered', totalAmount: 699 },
      { id: 'ORD005', customerName: 'Rohit Sharma', orderDate: new Date('2025-06-05'), status: 'Cancelled', totalAmount: 1599 },
    ];
    this.filteredOrders = this.orders;
  }

  onSearch(): void {
    const term = this.searchTerm.toLowerCase().trim();
    this.applyFilters(term, this.filterStatus);
  }

  onStatusChange(status: string): void {
    this.filterStatus = status;
    this.applyFilters(this.searchTerm.toLowerCase().trim(), status);
  }

  private applyFilters(term: string, status: string): void {
    this.filteredOrders = this.orders.filter(order => {
      const matchesSearch = order.customerName.toLowerCase().includes(term) || order.id.toLowerCase().includes(term);
      const matchesStatus = status === 'All' || order.status === status;
      return matchesSearch && matchesStatus;
    });
  }

  formatDate(date: Date): string {
    return new Date(date).toLocaleDateString();
  }

  updateOrderStatus(order: Order, event: Event) {
  const selectElement = event.target as HTMLSelectElement | null;
  if (!selectElement) return;  // null check to avoid errors

  const newStatus = selectElement.value as Order['status'];
  order.status = newStatus;
  order.orderDate = new Date();

  // Further processing like saving to server can be done here
}
}
