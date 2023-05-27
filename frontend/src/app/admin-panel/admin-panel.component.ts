import {Component, OnInit, ViewChild} from '@angular/core';
import {PlacePickerComponent} from "../placepicker/place-picker.component";
import {HttpClient} from "@angular/common/http";
import {Constants} from "../constants";

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css'],
  providers: [PlacePickerComponent]
})
export class AdminPanelComponent implements OnInit {
  userId: number = 0
  advertId: number = 0
  animalAdvertId: number = 0
  shelterIdToDelete: number = 0


  @ViewChild(PlacePickerComponent)
  private placeSelector!: PlacePickerComponent;
  constructor(private httpClient: HttpClient) { }

  ngOnInit(): void {
  }

  deleteUser() {
    this.httpClient.delete(Constants.api + 'users/delete?id=' + this.userId + '&token=' + this.token())
      .subscribe(r => console.log(r))
  }

  deleteAdvert() {
    this.httpClient.delete(Constants.api + 'adverts/delete?id=' + this.advertId + '&token=' + this.token())
      .subscribe(r => console.log(r))
  }

  deleteAnimalAdvert() {
    this.httpClient.delete(Constants.api + 'animalAdverts/delete?id=' + this.advertId + '&token=' + this.token())
      .subscribe(r => console.log(r))
  }

  private token() : string | null {
    return localStorage.getItem('jwt')
  }
}
