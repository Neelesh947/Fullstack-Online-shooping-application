import { Component } from '@angular/core';

interface InventoryItem {
  id: string;
  productName: string;
  sku: string;
  stockQuantity: number;
  price: number;
  lastUpdated: Date;
}

@Component({
  selector: 'app-inventory-management-component',
  standalone: false,
  templateUrl: './inventory-management-component.html',
  styleUrl: './inventory-management-component.css'
})
export class InventoryManagementComponent {

  inventoryItems: InventoryItem[] = [];

  filteredItems: InventoryItem[] = [];
  searchTerm: string = '';

  ngOnInit(): void {
    // Sample data — replace with real API call
    this.inventoryItems = [
      { id: '1', productName: 'Wireless Mouse', sku: 'WM123', stockQuantity: 120, price: 499, lastUpdated: new Date('2025-06-01') },
      { id: '2', productName: 'Bluetooth Headphones', sku: 'BH234', stockQuantity: 50, price: 1299, lastUpdated: new Date('2025-06-10') },
      { id: '3', productName: 'USB-C Charger', sku: 'UC345', stockQuantity: 200, price: 799, lastUpdated: new Date('2025-06-15') },
      { id: '4', productName: 'Mechanical Keyboard', sku: 'MK456', stockQuantity: 30, price: 2499, lastUpdated: new Date('2025-05-25') },
    ];
    this.filteredItems = this.inventoryItems;
  }

  onSearch(): void {
    const term = this.searchTerm.toLowerCase().trim();
    this.filteredItems = this.inventoryItems.filter(item =>
      item.productName.toLowerCase().includes(term) || item.sku.toLowerCase().includes(term));
  }

  formatDate(date: Date): string {
    return new Date(date).toLocaleDateString();
  }
}
