import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {User} from "../../shared/models";
import {Constants} from "../constants";
import {Router} from "@angular/router";

@Component({
  selector: 'app-change-info',
  templateUrl: './change-info.component.html',
  styleUrls: ['./change-info.component.css']
})
export class ChangeInfoComponent implements OnInit {
  name: string = ''
  surname: string = ''
  email: string = ''
  phoneNumber: string = ''
  user?: User
  constructor(private httpClient: HttpClient,
              private router: Router) { }

  ngOnInit(): void {
    let nameInput = document.querySelector('#nameInput') as HTMLInputElement
    let surnameInput = document.querySelector('#surnameInput') as HTMLInputElement
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
    emailInput.oninput = validator(emailInput, 3)
    phoneInput.oninput = (e) => {
      if (this.isValidPhone(this.phoneNumber)) {
        phoneInput.classList.remove('is-invalid')
      } else {
        phoneInput.classList.add('is-invalid')
      }
    }
    this.httpClient.get<User>(Constants.api + 'users/getSelfInfo?token=' + localStorage.getItem('jwt'))
      .subscribe(r => {
        this.user = r;
        this.email = r.email
        this.phoneNumber = r.phoneNumber
        this.name = r.name
        this.surname = r.surname
      })
  }


  isValidPhone(phone: string) : boolean {
    const first = /^375\d{9}$/i
    return phone.match(first) !== null
  }

  update() {
    let nameInput = document.querySelector('#nameInput') as HTMLInputElement
    let surnameInput = document.querySelector('#surnameInput') as HTMLInputElement
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
    if (this.email.length < 4) {
      valid = false;
      emailInput.classList.add('is-invalid');
    } else {
      emailInput.classList.remove('is-invalid')
    }
    if (this.phoneNumber.length < 7 || !this.isValidPhone(this.phoneNumber)) {
      valid = false;
      phoneInput.classList.add('is-invalid');
    } else {
      phoneInput.classList.remove('is-invalid')
    }
    if (!valid) {
      return
    }
    this.httpClient.post<HttpResponse<any>>(Constants.api + '/users/update?token=' + localStorage.getItem('jwt'), {
      'userId': this.user?.id,
      'name': this.name,
      'surname': this.surname,
      'login': null,
      'password': null,
      'providedPassword': null,
      'email': this.email,
      'phoneNumber': Number(this.phoneNumber)
    }, {
      headers: {
        'Content-type': 'text/json; charset=UTF-8'
      }
    }).subscribe(r => {
      alert('Обновлено')
      this.router.navigate(['account'])
    }, (e: HttpErrorResponse) => {
      alert('Произошла ошибка. Проверьте данные.')
    })
  }
}
