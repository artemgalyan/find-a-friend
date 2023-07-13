import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {Advert, Roles, User} from "../../shared/models";
import {Constants} from "../constants";
import {dateToString} from "../../shared/utils";
import {PlaceService} from "../../shared/PlaceService";
import {PlaceLoader} from "../../shared/PlaceLoader";

@Component({
  selector: 'app-advert',
  templateUrl: './advert.component.html',
  styleUrls: ['./advert.component.css']
})
export class AdvertComponent implements OnInit {
  advert?: Advert
  user?: User

  advertTypes: any = {
    0: 'Ситтер',
    1: 'Волонтёр'
  }

  readonly loader: PlaceLoader
  constructor(private route: ActivatedRoute,
              private httpClient: HttpClient,
              private router: Router,
              placeService: PlaceService) {
    this.loader = new PlaceLoader(placeService);
  }


  ngOnInit(): void {
    this.route.queryParams.subscribe(result => {
      if (result['id'] === null || result['id'] === undefined) {
        this.router.navigate(['adverts'])
        return
      }

      let id = result['id']
      this.httpClient.get<Advert>(Constants.api + '/adverts/getById?id=' + id).subscribe(r => {
        this.advert = r;
        this.httpClient.get<User>(Constants.api + '/users/getById?id=' + this.advert.ownerId + '&token=' + localStorage.getItem('jwt'))
          .subscribe(u => this.user = u,
            e => this.user = null!)
      })
    })
  }

  typeToString(type?: number): string {
    if (type === undefined || type === null) {
      return 'Загрузка..'
    }
    return this.advertTypes[type];
  }


  canDeleteAdvert(): boolean {
    let id = Number(localStorage.getItem('id'));
    let role = localStorage.getItem('role');
    let ownerId = this.advert!.ownerId
    return role === Roles.Administrator || role === Roles.Moderator
      || id === ownerId;
  }

  deleteAdvert() {
    this.httpClient.delete(Constants.api + 'adverts/delete?id=' + this.advert?.id + '&token=' + localStorage.getItem('jwt'))
      .subscribe(_ => {
        alert('Удалено')
        this.router.navigate(['adverts'])
      }, e => console.log(e));
  }

  readonly dateToString = dateToString;
}
