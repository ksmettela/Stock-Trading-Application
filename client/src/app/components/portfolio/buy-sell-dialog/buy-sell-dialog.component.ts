import { DecimalPipe } from '@angular/common';
import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

export interface BuySellData {
  stock: any,
  userId: Number,
  action: String,
  quantity: Number;
  orderType: string;
  unitPrice: Number;
  isLimit: boolean
}

@Component({
  selector: 'app-buy-sell-dialog',
  templateUrl: './buy-sell-dialog.component.html',
  styleUrls: ['./buy-sell-dialog.component.less']
})
export class BuySellDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<BuySellDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: BuySellData) {
    data.unitPrice = data.stock.basePrice;
    data.isLimit = false;
  }
  ngOnInit(): void { }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
