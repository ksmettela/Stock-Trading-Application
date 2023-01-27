import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { ApiService } from "../../services/api.service";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.less']
})
export class LoginComponent implements OnInit {
  public loginForm!: FormGroup;
  constructor(private formbuilder: FormBuilder, private apiService: ApiService,
    private authService: AuthService,
    private router: Router) { }

  ngOnInit(): void {
    this.loginForm = this.formbuilder.group({
      email: ['', Validators.required, Validators.email],
      password: ['', Validators.required]
    })
  }

  login() {
    this.apiService.post("user/login", this.loginForm.value).subscribe((res: any) => {
      if (res) {
        console.log('Login Succesful');
        console.log(res);
        this.authService.login(res);
        this.loginForm.reset();
        this.router.navigate(["home"])
      } else {
        console.log("user not found")
      }
    }, err => {
      console.error("Something went wrong")
    })
  }
}
