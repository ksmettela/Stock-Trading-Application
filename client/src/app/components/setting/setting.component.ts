import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ApiService } from 'src/app/services/api.service';

@Component({
  selector: 'app-setting',
  templateUrl: './setting.component.html',
  styleUrls: ['./setting.component.less']
})
export class SettingComponent implements OnInit {

  workingDays = new FormControl();
  workingDaysList!: any[];
  settings!: any;


  constructor(private formBuilder: FormBuilder, private apiService: ApiService) {
    this.workingDaysList = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
  }

  ngOnInit(): void {
    this.settings = {
      startHour: 0,
      startMinutes: 0,
      endHour: 0,
      endMinute: 0
    };

    this.apiService.get('stocks/settings').subscribe((s: any) => {
      if (s) {
        this.settings = s;
      }
    });
  }

  public save() {
    let days = [];
    this.workingDays.value.forEach(v => {
      let i = this.workingDaysList.indexOf(v);
      days.push(i);
    });
    this.settings.workingDays = days;
    if (this.settings.startHour > 0 && this.settings.endHour > this.settings.startHour && this.settings.endHour < 23 && days.length > 0) {
      this.apiService.post('stocks/settings', this.settings).subscribe(r => {
        console.log(r);
      });
    }
  }
}
