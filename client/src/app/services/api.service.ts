import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private SERVER = "http://localhost:8080/";

  constructor(private httpClient: HttpClient) { }

  public get(uri: String) {
    return this.httpClient.get(this.api(uri));
  }

  private api(uri: String): string {
    return `${this.SERVER}${uri}`;
  }

  public post(uri: String, body: any) {
    return this.httpClient.post(this.api(uri), body);
  }
}
