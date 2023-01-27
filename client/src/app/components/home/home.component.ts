import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.less']
})
export class HomeComponent implements OnInit {
  eventsSubject: Subject<void> = new Subject<void>();
  constructor() { }

  ngOnInit(): void {
  }
  onReloadStocks() {
    this.eventsSubject.next();
    console.log('onReloadStocks');
  }
}
