import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { RegisterUserComponent } from './components/register-user/register-user.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { StocksComponent } from './components/stocks/stocks.component';
import { SettingComponent } from './components/setting/setting.component';
import { AddStockComponent } from './components/stocks/add-stock/add-stock.component';
import { ListStockComponent } from './components/stocks/list-stock/list-stock.component';
import { PortfolioComponent } from './components/portfolio/portfolio.component';
import { TradeComponent } from './components/portfolio/trade/trade.component';
import { FundsComponent } from './components/portfolio/funds/funds.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from "@angular/material/icon";
import { MatTableModule } from '@angular/material/table';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from "@angular/material/select";
import {MatTabsModule} from '@angular/material/tabs';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import { BuySellDialogComponent } from './components/portfolio/buy-sell-dialog/buy-sell-dialog.component';
import {MatDialogModule} from '@angular/material/dialog';

@NgModule({
  declarations: [
    AppComponent,
    RegisterUserComponent,
    HomeComponent,
    LoginComponent,
    StocksComponent,
    SettingComponent,
    AddStockComponent,
    ListStockComponent,
    PortfolioComponent,
    TradeComponent,
    FundsComponent,
    BuySellDialogComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule, 
    MatButtonModule, MatInputModule, MatIconModule, MatTableModule, MatDialogModule,
    MatGridListModule, MatFormFieldModule, MatSelectModule,MatTabsModule,MatSlideToggleModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
