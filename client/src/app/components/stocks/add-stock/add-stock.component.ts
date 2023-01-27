import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ApiService } from 'src/app/services/api.service';

@Component({
  selector: 'app-add-stock',
  templateUrl: './add-stock.component.html',
  styleUrls: ['./add-stock.component.less']
})
export class AddStockComponent implements OnInit {
  public stockForm!: FormGroup;

  constructor(private formbuilder: FormBuilder, private apiService: ApiService) { }
  @Output() reloadStocks = new EventEmitter();

  ngOnInit(): void {
    this.stockForm = this.formbuilder.group({
      name: ['', Validators.required],
      basePrice: ['', Validators.required],
      volume: ['', Validators.required],
      margin: ['', Validators.required]
    });
  }
  save() {
    if (this.stockForm.valid) {
      console.log('add stock');
      this.apiService.post('stocks/add', this.stockForm.value).subscribe((r: any) => {
        console.log(r);

        this.reloadStocks.emit();
      });
    }
  }
}
