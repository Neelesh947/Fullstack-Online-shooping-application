import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-product-details-component',
  standalone: false,
  templateUrl: './product-details-component.html',
  styleUrl: './product-details-component.css'
})
export class ProductDetailsComponent {

  @Input() product: any;

  quantity: number = 1;

  addToCart() {
    console.log('Added to cart:', this.product.id, 'Qty:', this.quantity);
    // implement cart service logic here
  }

  buyNow() {
    console.log('Buy Now:', this.product.id, 'Qty:', this.quantity);
    // redirect to payment or checkout
  }

  addToWishlist() {
    console.log('Added to wishlist:', this.product.id);
    // implement wishlist logic here
  }
}
