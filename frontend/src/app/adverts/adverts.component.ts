import {Component, OnInit} from '@angular/core';
import {Advert, Place} from "../../shared/models";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {Constants} from "../constants";

@Component({
  selector: 'app-adverts',
  templateUrl: './adverts.component.html',
  styleUrls: ['./adverts.component.css']
})
export class AdvertsComponent implements OnInit {
  adverts: Advert[] = [];

  private readonly maxLength: number = 100;

  constructor(private httpClient: HttpClient,
              private router: Router) {
  }

  ngOnInit(): void {
    this.httpClient.get<Advert[]>(Constants.api + 'adverts/getAll')
      .subscribe(r => {
        this.adverts = r
        console.log(r)
      });
  }

  shorten(s: string): string {
    if (s.length < this.maxLength) {
      return s;
    }
    return s.substring(0, this.maxLength - 3) + '...';
  }

  openAdvert(advertId: number) {
    this.router.navigate(['advert'], {
      queryParams: {
        'advertId': advertId
      }
    });
  }


  placeToString(p: Place) : string {
    return p.city + ', ' + p.district;
  }

  getAdvertType(a: Advert) {
    return a.advertType == "VOLUNTEER"
      ? 'Волонтёр'
      : 'Ситтер'
  }
}
