import {Component, OnInit} from '@angular/core';
import {Place, Shelter} from "../../shared/models";
import {HttpClient} from "@angular/common/http";
import {Constants} from "../constants";
import {Router} from "@angular/router";

@Component({
  selector: 'app-shelters',
  templateUrl: './shelters.component.html',
  styleUrls: ['./shelters.component.css']
})
export class SheltersComponent implements OnInit {
  shelters: Shelter[] = []

  constructor(private httpClient: HttpClient,
              private router: Router) {
  }

  ngOnInit(): void {
    this.httpClient.get<Shelter[]>(Constants.api + 'shelters/getAll')
      .subscribe(r => this.shelters = r);
  }

  placeToString(p: Place): string {
    return p.city + ', ' + p.district;
  }

  viewShelter(id: number) {
    this.router.navigate(['shelter'], {
      queryParams: {
        'id': id
      }
    })
  }
}
