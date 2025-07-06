import { Component } from '@angular/core';

interface Store {
  id: string;
  name: string;
  owner: string;
  email: string;
  status: 'Active' | 'Suspended';
}

@Component({
  selector: 'app-manage-stores-component',
  standalone: false,
  templateUrl: './manage-stores-component.html',
  styleUrl: './manage-stores-component.css'
})
export class ManageStoresComponent {

  search = '';
  
  stores: Store[] = [
    { id: 'S001', name: 'Tech World', owner: 'Alice Sharma', email: 'alice@techworld.com', status: 'Active' },
    { id: 'S002', name: 'SmartMart', owner: 'Ravi Kumar', email: 'ravi@smartmart.com', status: 'Suspended' },
    { id: 'S003', name: 'DailyNeeds', owner: 'John Smith', email: 'john@dailyneeds.com', status: 'Active' },
  ];

  get filteredStores(): Store[] {
    const lower = this.search.toLowerCase();
    return this.stores.filter(store =>
      store.name.toLowerCase().includes(lower) || store.email.toLowerCase().includes(lower)
    );
  }

  toggleStatus(store: Store) {
    store.status = store.status === 'Active' ? 'Suspended' : 'Active';
  }

  removeStore(storeId: string) {
    this.stores = this.stores.filter(s => s.id !== storeId);
  }
}
