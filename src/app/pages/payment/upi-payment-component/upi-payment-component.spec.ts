import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpiPaymentComponent } from './upi-payment-component';

describe('UpiPaymentComponent', () => {
  let component: UpiPaymentComponent;
  let fixture: ComponentFixture<UpiPaymentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UpiPaymentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpiPaymentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
