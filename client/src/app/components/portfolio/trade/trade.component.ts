import { Component, Input, OnInit } from '@angular/core';
import { ApiService } from 'src/app/services/api.service';

@Component({
  selector: 'app-trade',
  templateUrl: './trade.component.html',
  styleUrls: ['./trade.component.less']
})
export class TradeComponent implements OnInit {
  @Input() type: String;

  userId: Number;
  records!: any[];
  headers: String[];
  constructor(public apiService: ApiService) {
    this.userId = 2;
  }
  ngOnInit(): void {
    console.log(this.type);
    if (this.type === 'transaction')
      this.getTransactions();
    else
      this.getOrders();
    this.headers = ["quantity", "price", "stock", "type", "status", "orderDate"];
  }
  
  public getOrders() {
    this.apiService.get(`portfolio/orders/${this.userId}`).subscribe((response: any[]) => {
      this.records = response;
    });
  }
  getTransactions() {
    this.apiService.get(`portfolio/transactions/${this.userId}`).subscribe((response: any[]) => {
      console.log(response);
      this.records = response;
    });
  }
}
