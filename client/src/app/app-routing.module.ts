
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from "./components/home/home.component";
import { LoginComponent } from "./components/login/login.component";
import { RegisterUserComponent } from "./components/register-user/register-user.component";
import { PortfolioComponent } from "./components/portfolio/portfolio.component";
import { AuthGuard } from './auth.guard';

const routes: Routes = [
  { path: "", redirectTo: "login", pathMatch: "full" },
  { path: "login", component: LoginComponent },
  { path: "register", component: RegisterUserComponent },
  {
    path: "home", component: HomeComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ADMIN'
    }
  },
  {
    path: 'portfolio', component: PortfolioComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'USER'
    }
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes),],
  exports: [RouterModule],

})
export class AppRoutingModule { }
