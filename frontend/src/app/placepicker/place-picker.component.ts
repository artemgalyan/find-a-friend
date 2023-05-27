import {Component, Injectable, OnInit} from '@angular/core';
import {Place} from "../../shared/models";
import {HttpClient} from "@angular/common/http";
import {Constants} from "../constants";
import {LoadingState} from "../../shared/utils";

@Component({
  selector: 'app-placepicker',
  templateUrl: './place-picker.component.html',
  styleUrls: ['./place-picker.component.css']
})
export class PlacePickerComponent implements OnInit {
  places: Place[] = [];
  state: LoadingState = LoadingState.Loading
  selectedPlace: Place = null!;

  constructor(private readonly httpClient: HttpClient) {

  }

  ngOnInit(): void {
    this.httpClient.get<Place[]>(Constants.api + 'places/getAll', {
      headers: {
        'Content-type': 'text/json; charset=UTF-8',
      }
    }).subscribe(r => {
      this.places = r
      this.state = LoadingState.Success
    }, e => this.state = LoadingState.Error)
  }
  public isSelected() : boolean {
    return this.selectedPlace !== null;
  }
  public getSelectedPlace() : Place {
    return this.selectedPlace;
  }

  updateSelected(p: Place) {
    this.selectedPlace = p;
  }
  readonly LoadingState = LoadingState;
}
