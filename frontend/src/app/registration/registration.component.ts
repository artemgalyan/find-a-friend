import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {Constants} from "../constants";
import {Router} from "@angular/router";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent {

  name: string = '';
  surname: string = '';
  login: string = '';
  password: string = '';
  email: string = '';
  phoneNumber: string = '';

  constructor(private httpClient: HttpClient,
              private router: Router) {}

  onButtonClicked() {
    this.httpClient.post<HttpResponse<any>>(Constants.api + '/users/createUser', {
      'name': this.name,
      'surname': this.surname,
      'login': this.login,
      'password': this.password,
      'email': this.email,
      'phoneNumber': this.phoneNumber
    }, {
      headers: {
        'Content-type': 'text/json; charset=UTF-8'
      }
    }).subscribe(r => {
      this.router.navigate(['login'])
    }, (e: HttpErrorResponse) =>{
      if (e.status == 400) {
        alert('Проверьте правильность введённой информации.');
      }
    })
  }
}
