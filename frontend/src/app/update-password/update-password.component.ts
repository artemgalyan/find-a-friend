import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {Router} from "@angular/router";
import {Constants} from "../constants";
import {User} from "../../shared/models";
import {quitAccount} from "../../shared/utils";

@Component({
  selector: 'app-update-password',
  templateUrl: './update-password.component.html',
  styleUrls: ['./update-password.component.css']
})
export class UpdatePasswordComponent implements OnInit {

  constructor(private httpClient: HttpClient,
              private router: Router) {
  }

  login: string = ''
  newPassword: string = ''
  oldPassword: string = ''
  id: number = -1

  ngOnInit(): void {
    let login = document.querySelector('#login-input') as HTMLInputElement
    let newPassword = document.querySelector('#password-input') as HTMLInputElement
    let oldPassword = document.querySelector('#repeat-password-input') as HTMLInputElement
    let validator = (element: HTMLInputElement, minSize: number = 1) => (e: Event) => {
      if (element.value.length < minSize) {
        element.classList.add('is-invalid')
      } else {
        element.classList.remove('is-invalid')
      }
    }
    login.oninput = validator(login)
    oldPassword.oninput = validator(oldPassword)
    newPassword.oninput = validator(newPassword, 8)
    this.httpClient.get<any>(Constants.api + 'users/getId?token=' + localStorage.getItem('jwt'))
      .subscribe(r => {
        this.id = r.userId
        this.login = r.login
      })
  }

  update() {
    let login = document.querySelector('#login-input') as HTMLInputElement
    let oldPassword = document.querySelector('#password-input') as HTMLInputElement
    let newPassword = document.querySelector('#repeat-password-input') as HTMLInputElement
    let valid = true;
    if (this.login.length === 0) {
      valid = false;
      login.classList.add('is-invalid')
    } else {
      login.classList.remove('is-invalid')
    }
    if (this.newPassword.length < 8) {
      valid = false;
      oldPassword.classList.add('is-invalid');
    } else {
      oldPassword.classList.remove('is-invalid')
    }
    if (this.oldPassword.length < 1) {
      valid = false;
      newPassword.classList.add('is-invalid');
    } else {
      newPassword.classList.remove('is-invalid')
    }

    if (!valid) {
      return
    }
    this.httpClient.post<boolean>(Constants.api + '/users/update?token=' + localStorage.getItem('jwt'), {

      'name': null,
      'userId': this.id,
      'surname': null,
      'login': this.login,
      'password': this.newPassword,
      'providedPassword': this.oldPassword,
      'email': null,
      'phoneNumber': null
    }, {
      headers: {
        'Content-type': 'text/json; charset=UTF-8'
      }
    }).subscribe(r => {
      alert('Обновлено')
      console.log(r)
      if (r) {
        quitAccount();
        this.router.navigate(['login'])
        return
      }
      this.router.navigate(['account'])
    }, (e: HttpErrorResponse) => {
      alert('Произошла ошибка. Проверьте данные.')
    })
  }


}
