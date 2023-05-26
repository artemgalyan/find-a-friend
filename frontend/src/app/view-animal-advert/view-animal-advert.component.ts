import {Component, Input, OnInit} from '@angular/core';
import {AnimalAdvert, AnimalAdvertWithPhoto, Photo, Place} from "../../shared/models";
import {HttpClient} from "@angular/common/http";
import {Constants} from "../constants";
import {DomSanitizer, SafeResourceUrl} from "@angular/platform-browser";
import {Router} from "@angular/router";

@Component({
  selector: 'app-view-animal-advert',
  templateUrl: './view-animal-advert.component.html',
  styleUrls: ['./view-animal-advert.component.css']
})
export class ViewAnimalAdvertComponent implements OnInit {
  @Input()
  advert!: AnimalAdvert

  photo: SafeResourceUrl = '';
  defaultPhoto: SafeResourceUrl = '';

  constructor(private httpClient: HttpClient,
              private sanitizer: DomSanitizer,
              private router: Router) {}

  ngOnInit(): void {
    this.httpClient.get<Photo>(Constants.api + 'photos/getPreview?id=' + this.advert.advertId).subscribe(res => {
      if (res !== null)
        this.photo = this.sanitizer.bypassSecurityTrustResourceUrl(res.base64content);
      else {
        this.photo = null!;
      }
    })

    if (this.advert.animalType === 'Кот') {
      this.defaultPhoto = 'assets/images/cat.png'
    } else if (this.advert.animalType === 'Собака') {
      this.defaultPhoto = 'assets/images/dog.png'
    } else {
      this.defaultPhoto = 'assets/images/monk.png'
    }
  }

  placeToString(p: Place) : string {
    return p.city + ', ' + p.district;
  }

  showAdvert() {
    this.router.navigate(['animalAdvert'], {queryParams: {
      'advertId': this.advert.advertId
    }})
  }
}
