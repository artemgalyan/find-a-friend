import {Component, OnInit} from '@angular/core';
import {Constants} from "../constants";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-moderator-tools',
  templateUrl: './moderator-tools.component.html',
  styleUrls: ['./moderator-tools.component.css']
})
export class ModeratorToolsComponent {
  advertId: number = 0
  animalAdvertId: number = 0

  addUserId: number = 0
  addShelterId: number = 0

  removeUserId: number = 0
  constructor(private httpClient: HttpClient) {}


  deleteAdvert() {
    this.httpClient.delete(Constants.api + 'adverts/delete?id=' + this.advertId + '&token=' + this.token())
      .subscribe(r => console.log(r))
  }

  deleteAnimalAdvert() {
    this.httpClient.delete(Constants.api + 'animalAdverts/delete?id=' + this.animalAdvertId + '&token=' + this.token())
      .subscribe(r => console.log(r))
  }

  removeUser() {
    this.httpClient.post(Constants.api + 'userShelter/removeUserFromShelter?userId=' + this.removeUserId + '&token=' + this.token(), {})
      .subscribe(_ => {}, e => console.log(e))
  }

  addUserToShelter() {
    this.httpClient.post(Constants.api + 'userShelter/addUserToShelter?userId=' + this.addUserId + '&shelterId=' + this.addShelterId
      + '&token=' + this.token(), {})
      .subscribe(_ => {}, e => console.log(e))
  }

  private token(): string | null {
    return localStorage.getItem('jwt')
  }
}
