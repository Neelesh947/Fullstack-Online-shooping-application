import { Component } from '@angular/core';

interface Role {
  id: string;
  name: string;
  description: string;
  permissions: string[];
}

@Component({
  selector: 'app-admin-roles-component',
  standalone: false,
  templateUrl: './admin-roles-component.html',
  styleUrl: './admin-roles-component.css'
})
export class AdminRolesComponent {

   roles: Role[] = [
    {
      id: '1',
      name: 'Admin',
      description: 'Full access to all features',
      permissions: ['read', 'write', 'delete'],
    },
    {
      id: '2',
      name: 'Editor',
      description: 'Can edit content',
      permissions: ['read', 'write'],
    },
    {
      id: '3',
      name: 'Viewer',
      description: 'Read-only access',
      permissions: ['read'],
    },
  ];

  selectedRole: Role | null = null;

  selectRole(role: Role) {
    this.selectedRole = { ...role }; // clone to avoid direct mutation if you want
  }

 updatePermissions(event: Event): void {
  if (!this.selectedRole) return; 
  const input = event.target as HTMLInputElement;
  const value = input.value;
  this.selectedRole.permissions = value
    .split(',')
    .map(p => p.trim())
    .filter(p => p);
}


  saveRole() {
    if (!this.selectedRole) return;

    const index = this.roles.findIndex(r => r.id === this.selectedRole!.id);
    if (index > -1) {
      this.roles[index] = { ...this.selectedRole };
      alert(`Role "${this.selectedRole.name}" saved successfully!`);
      this.selectedRole = null;
    }
  }

  cancelEdit() {
    this.selectedRole = null;
  }
}
