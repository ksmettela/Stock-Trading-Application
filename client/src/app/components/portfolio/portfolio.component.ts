import { Component, OnInit } from '@angular/core';
import { ApiService } from 'src/app/services/api.service';

@Component({
  selector: 'app-portfolio',
  templateUrl: './portfolio.component.html',
  styleUrls: ['./portfolio.component.less']
})
export class PortfolioComponent implements OnInit {

  order!: any[];
  userId: Number;
  funds: Number;
  transaction: "transaction";
  constructor(public apiService: ApiService) {
    this.userId = 2;
  }

  ngOnInit(): void {
    this.getFunds();
  }

  getFunds() {
    this.apiService.get(`portfolio/funds/${this.userId}`).subscribe((response: any) => {
      console.log(response);
      this.funds = response;
    });
  }
}
