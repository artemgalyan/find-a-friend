import {Component, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Shelter} from "../../shared/models";
import {Constants} from "../constants";
import {ActivatedRoute, Router} from "@angular/router";
import {PlacePickerComponent} from "../placepicker/place-picker.component";
import {PlaceService} from "../../shared/PlaceService";

@Component({
  selector: 'app-edit-shelter',
  templateUrl: './edit-shelter.component.html',
  styleUrls: ['./edit-shelter.component.css']
})
export class EditShelterComponent implements OnInit {
  shelter?: Shelter;
  shelterName: string = ''
  shelterAddress: string = '';
  shelterWebsite: string = ''
  shelterId: number = -1;
  @ViewChild(PlacePickerComponent)
  placePicker: PlacePickerComponent = null!;
  constructor(private httpClient: HttpClient,
              private route: ActivatedRoute,
              private router: Router,
              private placeService: PlaceService) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(p => {
      const id = p['id'];
      if (id === null || id === undefined) {
        this.router.navigate(['shelters'])
        return;
      }
      this.httpClient.get<Shelter>(Constants.api + '/shelters/getById?id=' + id)
        .subscribe(r => {
          this.shelter = r
          this.shelterName = r.name
          this.shelterAddress = r.address
          this.shelterWebsite = r.website
          this.shelterId = r.id
          this.placePicker.setSelected(r.placeId)
        })
    })
  }

  update() {
    this.httpClient.put(Constants.api + '/shelters/update?token=' + localStorage.getItem('jwt'), {
      'shelterId': this.shelterId,
      'name': this.shelterName,
      'placeId': this.placePicker.selectedPlace.id,
      'address': this.shelterAddress,
      'website': this.shelterWebsite
    }, {
      headers: {
        'Content-type': 'text/json; charset=UTF-8'
      }
    }).subscribe(r => alert('Обновлено'), e => console.log(e))
  }
}
