import {Component, OnInit, ViewChild} from '@angular/core';
import {PlacePickerComponent} from "../placepicker/place-picker.component";
import {HttpClient} from "@angular/common/http";
import {Constants} from "../constants";
import {Roles} from "../../shared/models";

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css'],
  providers: [PlacePickerComponent]
})
export class AdminPanelComponent {
  userId: number = 0
  shelterIdToDelete: number = 0


  @ViewChild('placeforshelter')
  private placeSelector!: PlacePickerComponent;
  shelterToCreateName: string = ''
  shelterAddress: string = ''
  shelterWebsite: string = ''

  @ViewChild('placetodelete')
  private toDeleteplaceSelector!: PlacePickerComponent;

  constructor(private httpClient: HttpClient) {
  }

  country: string = ''
  region: string = ''
  city: string = ''
  district: string = ''

  deleteUser() {
    this.httpClient.delete(Constants.api + 'users/delete?id=' + this.userId + '&token=' + this.token())
      .subscribe(r => console.log(r))
  }

  deleteShelter() {
    this.httpClient.delete(Constants.api + 'shelters/delete?id=' + this.shelterIdToDelete + '&token=' + this.token())
      .subscribe(r => console.log(r))
  }

  private token(): string | null {
    return localStorage.getItem('jwt')
  }

  createShelter() {
    this.httpClient.post(Constants.api + 'shelters/create?token=' + this.token(), {
      'name': this.shelterToCreateName,
      'placeId': this.placeSelector.selectedPlace.id,
      'address': this.shelterAddress,
      'website': this.shelterWebsite
    }, {
      headers: {
        'Content-type': 'text/json; charset=UTF-8'
      }
    }).subscribe(r => alert(r))
  }

  createPlace() {
    this.httpClient.post(Constants.api + 'places/create?token=' + this.token(), {
      'country': this.country,
      'region': this.region,
      'city': this.city,
      'district': this.district
    }, {
      headers: {
        'Content-type': 'text/json; charset=UTF-8'
      }
    }).subscribe(r => {}, e => console.log(e))
  }

  deletePlace() {
    this.httpClient.delete(Constants.api + 'places/delete?id=' + this.toDeleteplaceSelector.selectedPlace.id + '&token=' + this.token())
      .subscribe(r => {},
                e => console.log(e))
  }

}
