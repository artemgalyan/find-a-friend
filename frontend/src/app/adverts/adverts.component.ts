import {Component, OnInit} from '@angular/core';
import {Advert, AdvertType} from "../../shared/models";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {Constants} from "../constants";
import {placeToString} from "../../shared/utils";
import {PlaceService} from "../../shared/PlaceService";
import {PlaceLoader} from "../../shared/PlaceLoader";

@Component({
  selector: 'app-adverts',
  templateUrl: './adverts.component.html',
  styleUrls: ['./adverts.component.css']
})
export class AdvertsComponent implements OnInit {
  adverts: Advert[] = [];

  private readonly maxLength: number = 100;
  readonly loader: PlaceLoader
  constructor(private httpClient: HttpClient,
              private router: Router,
              placeService: PlaceService) {
    this.loader = new PlaceLoader(placeService);
  }

  ngOnInit(): void {
    this.httpClient.get<Advert[]>(Constants.api + '/adverts/getAll')
      .subscribe(r => {
        this.adverts = r
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
        'id': advertId
      }
    });
  }

  getAdvertType(a: Advert) {
    return a.advertType == AdvertType.Volunteer
      ? 'Волонтёр'
      : 'Ситтер'
  }

  readonly placeToString = placeToString;
}
