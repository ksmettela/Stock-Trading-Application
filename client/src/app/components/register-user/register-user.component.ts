import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiService } from 'src/app/services/api.service';


@Component({
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrls: ['./register-user.component.less']
})
export class RegisterUserComponent implements OnInit {

  public registerForm !: FormGroup;


  constructor(private formBuilder: FormBuilder, private apiService: ApiService, private router: Router) { }
  codes: any[];
  wd = '1';
  ngOnInit(): void {
    this.codes = ['93', '355', '213', '1-684', '376', '244', '1-264', '672', '1-268', '54', '374', '297', '61', '43', '994', '1-242', '973', '880', '1-246', '375', '32', '501', '229', '1-441', '975', '591', '387', '267', '55', '246', '1-284', '673', '359', '226', '257', '855', '237', '1', '238', '1-345', '236', '235', '56', '86', '61', '61', '57', '269', '682', '506', '385', '53', '599', '357', '420', '243', '45', '253', '1-767', '1-809, 1-829, 1-849', '670', '593', '20', '503', '240', '291', '372', '251', '500', '298', '679', '358', '33', '689', '241', '220', '995', '49', '233', '350', '30', '299', '1-473', '1-671', '502', '44-1481', '224', '245', '592', '509', '504', '852', '36', '354', '91', '62', '98', '964', '353', '44-1624'];

    this.registerForm = this.formBuilder.group({
      email: ["", Validators.email],
      password: ["", Validators.required],
      firstname: ["", Validators.required],
      lastname: ["", Validators.required],
      phone: ["", Validators.maxLength(10)]
    });
  }

  register() {
    this.apiService.post('user/register', this.registerForm.value)
      .subscribe(r => {
        console.log(r);
        console.log("Registration Successful");
        this.registerForm.reset();
        this.router.navigate(["login"]);
      }, err => {
        console.error(err);
      });
  }
}
