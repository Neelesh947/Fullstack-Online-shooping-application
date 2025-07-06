import { Injectable } from '@angular/core';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class SweetAlertService {

  constructor() { }

  confirmDelete(): Promise<boolean> {
    return Swal.fire({
      title: 'Are you sure?',
      text: 'This product will be permanently deleted!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#e74c3c',
      cancelButtonColor: '#6c757d',
      confirmButtonText: 'Yes, delete it!',
    }).then(result => result.isConfirmed);
  }

  success(message: string): void {
    Swal.fire('Success!', message, 'success');
  }

  error(message: string): void {
    Swal.fire('Error!', message, 'error');
  }
}
