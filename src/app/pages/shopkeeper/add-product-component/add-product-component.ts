import { Component, OnInit } from '@angular/core';
import { Product, ProductDto } from '../../../services/product';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-product-component',
  standalone: false,
  templateUrl: './add-product-component.html',
  styleUrl: './add-product-component.css'
})
export class AddProductComponent implements OnInit{

  product: Omit<ProductDto, 'id' | 'images'> = {
    name: '',
    description: '',
    price: 0,
  };

  selectedFiles: File[] = [];
  submitting = false;
  errorMessage = '';
  successMessage = '';

  constructor(private productService: Product,
    private router:Router
  ){}

  ngOnInit(): void {
    
  }

  onFileChange(event: any) {
    if (event.target.files && event.target.files.length) {
      this.selectedFiles = Array.from(event.target.files);
    }
  }

  onSubmit() {
    if (!this.product.name || !this.product.description || this.product.price <= 0) {
      this.errorMessage = 'Please fill all fields with valid data.';
      return;
    }
    if (this.selectedFiles.length === 0) {
      this.errorMessage = 'Please select at least one image.';
      return;
    }

    this.errorMessage = '';
    this.successMessage = '';
    this.submitting = true;

    this.productService.createProduct(this.product, this.selectedFiles).subscribe({
      next: (res) => {
        this.successMessage = 'Product created successfully!';
        this.submitting = false;
        // Reset form
        this.product = { name: '', description: '', price: 0 };
        this.selectedFiles = [];
        this.router.navigate(['/store-product-dashboard']);
      },
      error: (err) => {
        this.errorMessage = 'Failed to create product. Please try again.';
        console.error('Create product error:', err);
        this.submitting = false;
      }
    });
  }
}
