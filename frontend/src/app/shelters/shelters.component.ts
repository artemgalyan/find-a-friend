import {Component, OnInit} from '@angular/core';
import {Place, Shelter} from "../../shared/models";
import {HttpClient} from "@angular/common/http";
import {Constants} from "../constants";
import {Router} from "@angular/router";
import {placeToString} from "../../shared/utils";
import {PlaceLoader} from "../../shared/PlaceLoader";
import {PlaceService} from "../../shared/PlaceService";

@Component({
  selector: 'app-shelters',
  templateUrl: './shelters.component.html',
  styleUrls: ['./shelters.component.css']
})
export class SheltersComponent implements OnInit {
  shelters: Shelter[] = []
  readonly loader: PlaceLoader
  constructor(private httpClient: HttpClient,
              private router: Router,
              private placeService: PlaceService) {
    this.loader = new PlaceLoader(placeService);
  }

  ngOnInit(): void {
    this.httpClient.get<Shelter[]>(Constants.api + '/shelters/getAll')
      .subscribe(r => this.shelters = r);
  }

  viewShelter(id: number) {
    this.router.navigate(['shelter'], {
      queryParams: {
        'id': id
      }
    })
  }

  readonly placeToString = placeToString;
}
