import { Component, OnInit } from '@angular/core';
import {Advert, AnimalAdvert, AnimalAdvertWithPhoto, Photo, Place, User} from '../../shared/models';
import {HttpClient} from "@angular/common/http";
import {Constants} from "../constants";
import {DomSanitizer} from "@angular/platform-browser";
import {Router} from "@angular/router";

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {
  self: User = null!;
  adverts: AnimalAdvert[] = [];
  constructor(private httpClient: HttpClient,
              private _sanitizer: DomSanitizer,
              private router: Router) { }

  ngOnInit(): void {
    let token = localStorage.getItem('jwt');
    this.httpClient.get<User>(Constants.api + 'users/getSelfInfo?token=' + token, {
      headers: {
        'Content-type': 'text/json; charset=UTF-8',
        'Authentication': 'Bearer'
      }
    }).subscribe(r => this.self = r);
    this.fetchMine();
  }

  private fetchMine() {
    this.httpClient.get<AnimalAdvert[]>(Constants.api + 'animalAdverts/getMine?token=' + localStorage.getItem('jwt'))
      .subscribe(r => this.adverts = r);
  }

  placeToString(p: Place) : string {
    return p.city + ', ' + p.district;
  }

  showAdvert(advertId: number) {}

  onCreateNewClicked() {
    this.router.navigate(['createAnimalAdvert'])
  }
}
