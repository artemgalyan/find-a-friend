import {Component, OnInit} from '@angular/core';
import {AnimalAdvert, AnimalAdvertWithPhoto, Photo, Place} from "../../shared/models";
import {HttpClient} from "@angular/common/http";
import {Constants} from "../constants";
import {DomSanitizer, SafeResourceUrl} from "@angular/platform-browser";

@Component({
  selector: 'app-animaladverts',
  templateUrl: './animal-adverts.component.html',
  styleUrls: ['./animal-adverts.component.css']
})
export class AnimalAdvertsComponent implements OnInit {
  adverts: AnimalAdvertWithPhoto[] = [];

  constructor(private httpClient: HttpClient,
              private _sanitizer: DomSanitizer) {
  }

  ngOnInit(): void {
    this.fetchAdverts();
  }

  private fetchAdverts() {
    this.httpClient.get<AnimalAdvert[]>(Constants.api + 'animalAdverts/getAll')
      .subscribe(r => {
        for (let advert of r) {
          this.httpClient.get<Photo>(Constants.api + 'photos/getPreview?id=' + advert.advertId).subscribe(res => {
            const image = this._sanitizer.bypassSecurityTrustResourceUrl(res.base64content);
            this.adverts.push(new AnimalAdvertWithPhoto(advert, image));
            console.log(res?.base64content)
          })
        }
      });
  }

  placeToString(p: Place) : string {
    return p.city + ', ' + p.district;
  }

  showAdvert(advertId: number) {}
}
