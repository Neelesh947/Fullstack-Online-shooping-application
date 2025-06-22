import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShopkeeperSignUpComponent } from './shopkeeper-sign-up-component';

describe('ShopkeeperSignUpComponent', () => {
  let component: ShopkeeperSignUpComponent;
  let fixture: ComponentFixture<ShopkeeperSignUpComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ShopkeeperSignUpComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ShopkeeperSignUpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
