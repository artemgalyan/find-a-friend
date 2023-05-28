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
    const passwordInput = document.getElementById('password-input')!;
    const loginInput = document.getElementById('login-input')!;
    if (this.login.length === 0) {
      loginInput.classList.add('is-invalid')
      return
    } else {
      loginInput.classList.remove('is-invalid')
    }
    this.httpClient.get<any>(Constants.api + 'auth/signIn?login=' + this.login + '&password=' + this.password, {
      headers: {
        'Content-type': 'text/plain'
      }
    }).subscribe((r) => {
      if (r === null) {
        passwordInput!.classList.add('is-invalid')
        return
      }

      localStorage.setItem('jwt', r.token);
      localStorage.setItem('id', r.userId)
      localStorage.setItem('role', r.role)
      localStorage.setItem('shelter_id', r.shelterId)
      this.router.navigate(['']);
    }, error => {
      alert('Произошла ошибка.')
    })
  }
}
