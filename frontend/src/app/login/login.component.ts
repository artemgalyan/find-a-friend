import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Constants} from "../constants";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  login: string = '';
  password: string = '';

  constructor(private httpClient: HttpClient,
              private router: Router) {}

  ngOnInit(): void {}

  onButtonClicked() {
    this.httpClient.get<any>(Constants.api + 'auth/signIn?login=' + this.login + '&password=' + this.password, {
      headers: {
        'Content-type': 'text/plain'
      }
    }).subscribe((r) => {
      if (r === null) {
        alert('Неверное имя пользователя или пароль.')
        return
      }

      localStorage.setItem('jwt', r.token);
      localStorage.setItem('id', r.userId)
      localStorage.setItem('role', r.role)
      this.router.navigate(['animalAdverts']);
    })
  }
}
