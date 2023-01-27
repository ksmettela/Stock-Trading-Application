import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Observable, Subscription } from 'rxjs';
import { ApiService } from 'src/app/services/api.service';
import { BuySellDialogComponent } from '../../portfolio/buy-sell-dialog/buy-sell-dialog.component';

@Component({
  selector: 'app-list-stock',
  templateUrl: './list-stock.component.html',
  styleUrls: ['./list-stock.component.less']
})
export class ListStockComponent implements OnInit {
  @Input() caller: String;
  public stocks!: Array<any>;
  public headers!: Array<any>;
  userId: number;
  private eventsSubscription: Subscription;

  @Input() events: Observable<void>;

  constructor(public dialog: MatDialog, private apiService: ApiService) {
    this.userId = 2;
  }

  ngOnInit(): void {

    this.headers = ["name", "basePrice", "volume", "capitalization"];
    if (this.caller == 'portfolio') {
      this.headers.push("id")
    }
    this.getStocks();
    this.eventsSubscription = this.events.subscribe(() => this.getStocks());
  }

  private getStocks() {
    this.apiService.get('/stocks').subscribe((r: any[]) => {
      console.log(r);
      if (r.length > 0)
        r.forEach(el => {
          el['capitalization'] = el.basePrice * el.volume;
        });
      this.stocks = r;
    });
  }
  openDialog(args) {
    args.userId = this.userId;
    const dialogRef = this.dialog.open(BuySellDialogComponent, {
      width: '20em',
      data: args
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      console.log(result);

      if (result && result !== undefined && result.userId !== undefined && result.quantity && result.quantity > 0) {
        if (result.action == 'buy') {
          result.orderType = result.isLimit === true ? 'BUY_ON_LIMIT' : 'BUY_ON_PRICE';
        }
        else if (result.action == 'sell') {
          result.orderType = result.isLimit === true ? 'SELL_ON_LIMIT' : 'SELL_ON_PRICE';
        }
        result.stockId = result.stock.id;
        delete result.action;
        delete result.isLimit;
        delete result.stock;
        this.placeOrder(result);
      }
    });
  }
  placeOrder(order: any) {
    this.apiService.post(`portfolio/place-order`, order).subscribe((response: any[]) => {
      console.log(response);
    });
  }
  ngOnDestroy() {
    this.eventsSubscription.unsubscribe();
  }
}
