import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {Constants} from "../constants";
import {Router} from "@angular/router";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  name: string = '';
  surname: string = '';
  login: string = '';
  password: string = '';
  email: string = '';
  phoneNumber: string = '';
  loginErrorMessage = 'Введите логин'

  constructor(private httpClient: HttpClient,
              private router: Router) {
  }

  onButtonClicked() {
    let nameInput = document.querySelector('#nameInput') as HTMLInputElement
    let surnameInput = document.querySelector('#surnameInput') as HTMLInputElement
    let loginInput = document.querySelector('#loginInput') as HTMLInputElement
    let passwordInput = document.querySelector('#passwordInput') as HTMLInputElement
    let emailInput = document.querySelector('#emailInput') as HTMLInputElement
    let phoneInput = document.querySelector('#phoneInput') as HTMLInputElement
    let valid = true;
    if (this.name.length === 0) {
      valid = false;
      nameInput.classList.add('is-invalid')
    } else {
      nameInput.classList.remove('is-invalid')
    }
    if (this.surname.length === 0) {
      valid = false;
      surnameInput.classList.add('is-invalid');
    } else {
      surnameInput.classList.remove('is-invalid')
    }
    if (this.login.length === 0) {
      valid = false;
      this.loginErrorMessage = 'Введите логин'
      loginInput.classList.add('is-invalid');
    } else {
      loginInput.classList.remove('is-invalid')
    }
    if (this.password.length < 8) {
      valid = false;
      passwordInput.classList.add('is-invalid');
    } else {
      passwordInput.classList.remove('is-invalid')
    }
    if (this.email.length < 4) {
      valid = false;
      emailInput.classList.add('is-invalid');
    } else {
      emailInput.classList.remove('is-invalid')
    }
    if (this.phoneNumber.length < 7) {
      valid = false;
      phoneInput.classList.add('is-invalid');
    } else {
      phoneInput.classList.remove('is-invalid')
    }
    if (!valid) {
      return
    }
    this.httpClient.post<HttpResponse<any>>(Constants.api + '/users/create', {
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
    }, (e: HttpErrorResponse) => {
      if (e.status == 400) {
        this.loginErrorMessage = 'Логин занят'
        loginInput.classList.add('is-invalid')
      }
    })
  }

  ngOnInit(): void {
    let nameInput = document.querySelector('#nameInput') as HTMLInputElement
    let surnameInput = document.querySelector('#surnameInput') as HTMLInputElement
    let loginInput = document.querySelector('#loginInput') as HTMLInputElement
    let passwordInput = document.querySelector('#passwordInput') as HTMLInputElement
    let emailInput = document.querySelector('#emailInput') as HTMLInputElement
    let phoneInput = document.querySelector('#phoneInput') as HTMLInputElement
    let validator = (element: HTMLInputElement, minSize: number = 1) => (e: Event) => {
      if (element.value.length < minSize) {
        element.classList.add('is-invalid')
      } else {
        element.classList.remove('is-invalid')
      }
    }
    nameInput.oninput = validator(nameInput)
    surnameInput.oninput = validator(surnameInput)
    loginInput.oninput = validator(loginInput)
    passwordInput.oninput = validator(passwordInput, 8)
    emailInput.oninput = validator(emailInput, 3)
    phoneInput.oninput = validator(phoneInput, 7)
  }
}
