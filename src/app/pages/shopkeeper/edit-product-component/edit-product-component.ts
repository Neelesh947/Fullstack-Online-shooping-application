import { Component, OnInit } from '@angular/core';
import { Product, ProductDto } from '../../../services/product';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-edit-product-component',
  standalone: false,
  templateUrl: './edit-product-component.html',
  styleUrl: './edit-product-component.css'
})
export class EditProductComponent implements OnInit{

  productId: string = '';
  product: ProductDto ={
    id: '',
    name: '',
    description:'',
    price: 0,
    quantity: 1,
    images: [],
  }

  images: File[] = [];

  constructor(private productService: Product, private router:Router, private route: ActivatedRoute){}

  ngOnInit(): void {
    this.productId = this.route.snapshot.params['productId'];
    console.log(this.productId);
    if(this.productId){
      this.productService.getProductById(this.productId).subscribe({
        next: (data) =>{
          let parsed = data;
        if (typeof data === 'string') {
          parsed = JSON.parse(data);
        }
          this.product = parsed[0];
          console.log(this.product);
        },
        error: (err) =>{
          console.error('Failed to load product:', err);
        }
      })
    }
  }

  onFileChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.images = Array.from(input.files);
    }
  }

  onSubmit(){
    this.productService.updateProduct(this.productId, this.product, this.images).subscribe({
      next: () =>{
        alert('Product Successfully updated...');

        this.router.navigate(['/store-product-dashboard']);
      },
        error: err => {
        console.error(err);
        alert('Failed to update product.');
      }
    });
  }
}
