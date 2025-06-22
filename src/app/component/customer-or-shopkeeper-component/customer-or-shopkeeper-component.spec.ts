import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerOrShopkeeperComponent } from './customer-or-shopkeeper-component';

describe('CustomerOrShopkeeperComponent', () => {
  let component: CustomerOrShopkeeperComponent;
  let fixture: ComponentFixture<CustomerOrShopkeeperComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CustomerOrShopkeeperComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CustomerOrShopkeeperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
