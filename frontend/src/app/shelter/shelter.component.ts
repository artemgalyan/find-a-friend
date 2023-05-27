import { Component, OnInit } from '@angular/core';
import {Place, Shelter} from "../../shared/models";
import {HttpClient} from "@angular/common/http";
import {Constants} from "../constants";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-shelter',
  templateUrl: './shelter.component.html',
  styleUrls: ['./shelter.component.css']
})
export class ShelterComponent implements OnInit {
  shelter?: Shelter;
  constructor(private httpClient: HttpClient,
              private route: ActivatedRoute,
              private router: Router) {}

  ngOnInit(): void {

    this.route.queryParams.subscribe(params => {
      let shelterId = params['id'];
      if (shelterId === undefined || shelterId === null) {
        this.router.navigate(['shelters'])
        return;
      }
      this.httpClient.get<Shelter>(Constants.api + 'shelters/getShelter?id=' + shelterId)
        .subscribe(result => this.shelter = result, error => alert('Произошла ошибка'))
    })
  }

  placeToString(p?: Place) : string {
    if (p === null || p === undefined) {
      return 'Не загружено'
    }
    return p.city + ', ' + p.district;
  }
}
