import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AnimalAdvert, Photo, Roles, User} from "../../shared/models";
import {HttpClient} from "@angular/common/http";
import {Constants} from "../constants";

@Component({
  selector: 'app-animal-advert',
  templateUrl: './animal-advert.component.html',
  styleUrls: ['./animal-advert.component.css']
})
export class AnimalAdvertComponent implements OnInit {
  advert!: AnimalAdvert
  photos: string[] = []
  currentPhoto: number = 0
  user!: User;

  constructor(private route: ActivatedRoute,
              private httpClient: HttpClient,
              private router: Router) {
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(r => {
      let advertId = r['advertId']
      if (advertId === null || advertId === undefined) {
        this.router.navigate(['animalAdverts'])
        return
      }

      this.httpClient.get<AnimalAdvert>(Constants.api + 'animalAdverts/getById?id=' + advertId)
        .subscribe(advert => {
          this.advert = advert;
          this.httpClient.get<Photo[]>(Constants.api + 'photos/getByAdvertId?id=' + advertId)
            .subscribe(photos => this.photos = photos.map(p => p.base64content))
          if (this.advert.shelterName === null) {
            this.httpClient.get<User>(Constants.api + 'users/getById?id=' + advert.userId + '&token=' + localStorage.getItem('jwt'))
              .subscribe(u => this.user = u,
                e => this.user = null!)
          }
        })
    })
  }

  dateToString(d: string): string {
    if (d === null) {
      return ""
    }
    let date = new Date(d)
    return date.getDay().toString().padStart(2, '0') + '.' + date.getMonth().toString().padStart(2, '0') + '.' + date.getFullYear()
  }

  next() {
    this.currentPhoto = (this.currentPhoto + 1) % this.photos.length;
  }
  previous() {
    if (this.currentPhoto === 0) {
      this.currentPhoto = this.photos.length - 1;
    } else {
      this.currentPhoto = this.currentPhoto - 1;
    }
  }

  authorIsShelter() : boolean {
    return this.advert.shelterName !== null && this.advert.shelterName !== undefined;
  }

  showAdvert(advert: AnimalAdvert) {
    this.router.navigate(['shelter'], {queryParams: {'id': advert.shelterId}})
  }

  canDeleteAdvert() : boolean {
    let shelterId = Number(localStorage.getItem('shelter_id'));
    let id = Number(localStorage.getItem('id'));
    let role = localStorage.getItem('role');
    return (this.authorIsShelter() && shelterId === this.advert.shelterId && role === Roles.ShelterAdministrator)
      || role === Roles.Administrator || role === Roles.Moderator
      || (role === Roles.User && id === this.advert.userId);
  }

  deleteAdvert() {
    this.httpClient.delete(Constants.api + 'animalAdverts/delete?id=' + this.advert.advertId + '&token=' + localStorage.getItem('jwt'))
      .subscribe(_ => {
        alert('Удалено')
        this.router.navigate(['animalAdverts'])
      }, e => console.log(e));
  }
}
