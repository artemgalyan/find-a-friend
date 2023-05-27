import {Component, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Constants} from "../constants";
import {PlacePickerComponent} from "../placepicker/place-picker.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-create-advert',
  templateUrl: './create-advert.component.html',
  styleUrls: ['./create-advert.component.css'],
  providers: [PlacePickerComponent]
})
export class CreateAdvertComponent implements OnInit {
  title: string = '';
  description: string = '';
  creationDate: string = '';
  advertType: string = '';
  placeId: number = 1;
  readonly advertTypes: string[] = ['V', 'S'];

  @ViewChild(PlacePickerComponent)
  private placeSelector!: PlacePickerComponent;

  constructor(private httpClient: HttpClient,
              private router: Router) {
  }

  ngOnInit(): void {
    if (!this.isLoggedIn()) {
      this.router.navigate(['login'])
      return
    }
  }

  isLoggedIn() : boolean {
    return localStorage.getItem('jwt') !== null && localStorage.getItem('jwt') !== undefined
  }

  createAdvert() {
    this.httpClient.post(Constants.api + 'adverts/create?token=' + localStorage.getItem('jwt'), {
      'advertType': this.advertType,
      'title': this.title,
      'description': this.description,
      'creationDate': this.creationDate,
      'placeId': this.placeSelector.selectedPlace.id
    }, {
      headers: {
        'Content-type': 'text/json; charset=UTF-8'
      }
    }).subscribe(r => this.router.navigate(['adverts']))
  }

  getAdvertType(s: string) : string {
    if (s === 'V') {
      return 'Волонтер';
    }
    return 'Ситтер';
  }
}
