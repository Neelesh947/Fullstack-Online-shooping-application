import { Component } from '@angular/core';

interface Customer {
  id: string;
  name: string;
  email: string;
  phone: string;
  address: string;
  registeredDate: Date;
}

@Component({
  selector: 'app-customer-management-component',
  standalone: false,
  templateUrl: './customer-management-component.html',
  styleUrl: './customer-management-component.css'
})
export class CustomerManagementComponent {

  customers: Customer[] = [];
  filteredCustomers: Customer[] = [];
  searchTerm: string = '';

  ngOnInit() {
    // Mock data - replace with API call
    this.customers = [
      { id: 'C001', name: 'Alice Johnson', email: 'alice@example.com', phone: '9876543210', address: '123, Elm Street', registeredDate: new Date('2023-01-15') },
      { id: 'C002', name: 'Bob Smith', email: 'bob@example.com', phone: '9123456789', address: '456, Oak Avenue', registeredDate: new Date('2023-02-10') },
      { id: 'C003', name: 'Charlie Brown', email: 'charlie@example.com', phone: '9988776655', address: '789, Pine Lane', registeredDate: new Date('2023-03-05') },
    ];
    this.filteredCustomers = this.customers;
  }

  search() {
    const term = this.searchTerm.trim().toLowerCase();
    this.filteredCustomers = term ? this.customers.filter(c =>
      c.name.toLowerCase().includes(term) ||
      c.email.toLowerCase().includes(term) ||
      c.phone.includes(term)
    ) : this.customers;
  }

  deleteCustomer(id: string) {
    if (confirm('Are you sure you want to delete this customer?')) {
      this.customers = this.customers.filter(c => c.id !== id);
      this.search(); // update filtered list
    }
  }
}
