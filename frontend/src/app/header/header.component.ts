import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {

  constructor(private router: Router) { }

  onAnimalAdvertsClicked() {
    this.router.navigate(['animalAdverts']);
  }

  onAccountClicked() {
    if (this.isLoggedIn()) {
      this.router.navigate(['account'])
    } else {
      this.router.navigate(['login'])
    }
  }

  onAdvertsClicked() {
   this.router.navigate(['adverts']);
  }

  isLoggedIn() : boolean {
    return localStorage.getItem('jwt') !== null && localStorage.getItem('jwt') !== undefined
  }

  onExit() {
    localStorage.removeItem('jwt')
    this.router.navigate(['login'])
  }
}
