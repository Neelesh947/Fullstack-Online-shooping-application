import { Component } from '@angular/core';

interface User {
  id: string;
  name: string;
  email: string;
  role: string;
  status: 'Active' | 'Suspended';
}

@Component({
  selector: 'app-manage-users-component',
  standalone: false,
  templateUrl: './manage-users-component.html',
  styleUrl: './manage-users-component.css'
})
export class ManageUsersComponent {

  searchText: string = '';

  users: User[] = [
    { id: 'U001', name: 'Alice Sharma', email: 'alice@example.com', role: 'Customer', status: 'Active' },
    { id: 'U002', name: 'Raj Patel', email: 'raj@example.com', role: 'Shopkeeper', status: 'Suspended' },
    { id: 'U003', name: 'John Doe', email: 'john@example.com', role: 'Customer', status: 'Active' },
    { id: 'U004', name: 'Sara Khan', email: 'sara@example.com', role: 'Customer', status: 'Active' }
  ];

  get filteredUsers(): User[] {
    return this.users.filter(user =>
      user.name.toLowerCase().includes(this.searchText.toLowerCase()) ||
      user.email.toLowerCase().includes(this.searchText.toLowerCase())
    );
  }

  toggleStatus(user: User) {
    user.status = user.status === 'Active' ? 'Suspended' : 'Active';
  }

  deleteUser(userId: string) {
    this.users = this.users.filter(user => user.id !== userId);
  }
}
