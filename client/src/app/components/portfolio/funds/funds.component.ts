import { Component, OnInit } from '@angular/core';
import { ApiService } from 'src/app/services/api.service';

@Component({
  selector: 'app-funds',
  templateUrl: './funds.component.html',
  styleUrls: ['./funds.component.less']
})
export class FundsComponent implements OnInit {

  userId: Number;
  amount: Number;
  action: String;

  constructor(public apiService: ApiService) {
    this.userId = 2;
  }

  ngOnInit(): void {
  }
  placeOrder() {
    let uri = `portfolio/${this.action.toLowerCase()}/${this.userId}/amount/${this.amount}`;
    console.log(uri);
    this.apiService.post(uri, {}).subscribe((r: any) => {
      console.log(r);
    });
  }
}
