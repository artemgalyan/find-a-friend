import {Component, OnInit} from '@angular/core';
import {Advert, AnimalAdvert, AnimalAdvertWithPhoto, Photo, Place} from "../../shared/models";
import {HttpClient} from "@angular/common/http";
import {Constants} from "../constants";
import {DomSanitizer, SafeResourceUrl} from "@angular/platform-browser";
import {Router} from "@angular/router";

@Component({
  selector: 'app-animal-adverts',
  templateUrl: './animal-adverts.component.html',
  styleUrls: ['./animal-adverts.component.css']
})
export class AnimalAdvertsComponent implements OnInit {
  adverts: AnimalAdvert[] = [];

  constructor(private httpClient: HttpClient,
              private router: Router) {
  }

  ngOnInit(): void {
    this.fetchAdverts();
  }

  private fetchAdverts() {
    this.httpClient.get<AnimalAdvert[]>(Constants.api + 'animalAdverts/getAll')
      .subscribe(r => this.adverts = r);
  }

  placeToString(p: Place) : string {
    return p.city + ', ' + p.district;
  }
}
