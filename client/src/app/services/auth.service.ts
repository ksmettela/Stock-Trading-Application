import { Injectable } from '@angular/core';
import { of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  isUserLoggedIn = false;
  role = undefined;
  constructor() { }
  login(userContext) {
    if (userContext && userContext.role && userContext.role !== null) {
      userContext.password = '********';
      localStorage.setItem('CONTEXT', userContext);
      localStorage.setItem('ROLE', userContext.role);
      this.isUserLoggedIn = true;
      this.role = userContext.role;
    }
    return of({ success: this.isUserLoggedIn, role: this.role });
  }
  logout() {
    this.isUserLoggedIn = false;
    this.role = undefined;
    localStorage.setItem('CONTEXT', undefined);
    localStorage.setItem('ROLE', undefined);
    return of({ success: this.isUserLoggedIn, role: this.role });
  }

  isLoggedIn() {
    const loggedIn = localStorage.getItem('CONTEXT');
    if (loggedIn!==undefined)
      this.isUserLoggedIn = true;
    else
      this.isUserLoggedIn = false;
    return this.isUserLoggedIn;
  }

  getRole() {
    this.role = localStorage.getItem('ROLE');
    return this.role;
  }
}
