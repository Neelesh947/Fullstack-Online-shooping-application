import { TestBed } from '@angular/core/testing';

import { LoginSerice } from './login-serice';

describe('LoginSerice', () => {
  let service: LoginSerice;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoginSerice);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
