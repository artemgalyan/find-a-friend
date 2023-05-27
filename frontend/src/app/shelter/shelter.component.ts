import {Component, OnInit, Sanitizer} from '@angular/core';
import {AnimalAdvert, Place, Shelter} from "../../shared/models";
import {HttpClient} from "@angular/common/http";
import {Constants} from "../constants";
import {ActivatedRoute, Router} from "@angular/router";
import {DomSanitizer, SafeResourceUrl} from "@angular/platform-browser";

@Component({
  selector: 'app-shelter',
  templateUrl: './shelter.component.html',
  styleUrls: ['./shelter.component.css'],
})
export class ShelterComponent implements OnInit {


  shelter?: Shelter;
  adverts: AnimalAdvert[] = [];
  mapsURL: SafeResourceUrl = 'Минск'
  constructor(private httpClient: HttpClient,
              private route: ActivatedRoute,
              private router: Router,
              private sanitizer: DomSanitizer) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      let shelterId = params['id'];
      if (shelterId === undefined || shelterId === null) {
        this.router.navigate(['shelters'])
        return;
      }
      this.httpClient.get<Shelter>(Constants.api + 'shelters/getById?id=' + shelterId)
        .subscribe(result => {
          this.shelter = result
          this.mapsURL = this.mapsLink(this.shelter.address)
          this.httpClient.get<AnimalAdvert[]>(Constants.api + 'animalAdverts/getByShelterId?id=' + shelterId)
            .subscribe(r => this.adverts = r);
        }, error => this.router.navigate(['shelters']))
    })
  }

  placeToString(p?: Place) : string {
    if (p === null || p === undefined) {
      return 'Не загружено'
    }
    return p.city + ', ' + p.district;
  }

  mapsLink(address: string) : SafeResourceUrl {
    return this.sanitizer.bypassSecurityTrustResourceUrl(
      `https://maps.google.com/maps?q=${address}&t=&z=20&ie=UTF8&iwloc=&output=embed`
    );
  }

  scrollDown() {
    const view = document.getElementById('adverts')
    view?.scrollIntoView(true)
  }
}
