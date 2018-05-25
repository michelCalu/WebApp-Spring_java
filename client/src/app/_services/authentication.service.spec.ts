import { TestBed, async } from '@angular/core/testing';
import { AuthenticationService } from '../_services';
import { User } from '../_models/user.model';

describe('Service: Authentication', () => {
  
  let service: AuthenticationService ;
  
  beforeEach(() => {
    service = new AuthenticationService(null,null,null,null,null);
  });
  
   afterEach(() => { 
    service = null;
    sessionStorage.removeItem('currentUser');
  });
  
  it('isLoggedIn should return true when user is present in sessionStorage', () => {
    let user = new User();
    user.id = 1;
    user.type = "citizen";
    sessionStorage.setItem('currentUser',JSON.stringify(user));
    expect(service.isLoggedIn).toBeTruthy;
  });
  
  it('after logout, sessionStorage should no longer contain a user', () => {
    let user = new User();
    user.id = 1;
    user.type = "citizen";
    sessionStorage.setItem('currentUser',JSON.stringify(user));
    service.logout;
    expect(sessionStorage.getItem('currentUser')).toBeNull;
  });
  
});
