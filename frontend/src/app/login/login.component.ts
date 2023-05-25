import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Constants} from "../constants";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  login: string = '';
  password: string = '';

  constructor(private httpClient: HttpClient) {}

  ngOnInit(): void {}

  onButtonClicked() {
    this.httpClient.get<string>(Constants.api + 'auth/signin?login=' + this.login + '&password=' + this.password, {
      headers: {
        'Content-type': 'text/plain'
      }
    }).subscribe((r) => {
      if (r === null || r === '') {
        alert('Неверное имя пользователя или пароль.')
        return
      }

      localStorage.setItem('jwt', r);
    })
  }
}
