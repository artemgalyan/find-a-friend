import {Component, ViewChild} from '@angular/core';
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

  setRoleUserId: number = 0
  roles = [Roles.Moderator, Roles.User]
  setRoleUserRole?: string

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
      .subscribe(r => alert('Done'))
  }

  deleteShelter() {
    this.httpClient.delete(Constants.api + 'shelters/delete?id=' + this.shelterIdToDelete + '&token=' + this.token())
      .subscribe(r => alert('Done'))
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
    }).subscribe(r => alert('Done'))
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
    }).subscribe(r => alert('Done'), e => console.log(e))
  }

  deletePlace() {
    this.httpClient.delete(Constants.api + 'places/delete?id=' + this.toDeleteplaceSelector.selectedPlace.id + '&token=' + this.token())
      .subscribe(r => {},
                e => console.log(e))
  }

  updateRole() {
    this.httpClient.put(Constants.api + 'users/setRole?token=' + localStorage.getItem('jwt'), {
      'userId': this.setRoleUserId,
      'role': this.setRoleUserRole
    }).subscribe(_ => alert('Done'))
  }
}
