import {Component, OnInit, ViewChild} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {Constants} from "../constants";
import {PlacePickerComponent} from "../placepicker/place-picker.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-create-advert',
  templateUrl: './create-advert.component.html',
  styleUrls: ['./create-advert.component.css'],
  providers: [PlacePickerComponent]
})
export class CreateAdvertComponent implements OnInit {
  title: string = '';
  description: string = '';
  advertType: string = '';
  placeId: number = -1;
  readonly advertTypes: string[] = ['V', 'S'];

  @ViewChild(PlacePickerComponent)
  private placeSelector!: PlacePickerComponent;

  constructor(private httpClient: HttpClient,
              private router: Router) {
  }

  typeToString(type: string): string {
    return type == 'V' ? 'Волонтёр' : 'Ситтер'
  }

  ngOnInit(): void {
    if (!this.isLoggedIn()) {
      this.router.navigate(['login'])
      return
    }

    let titleInput = document.querySelector('#titleInput') as HTMLInputElement
    let descriptionInput = document.querySelector('#descriptionInput') as HTMLInputElement
    let advertTypeInput = document.querySelector('#advertTypeInput') as HTMLInputElement
    let placeIdInput = document.querySelector('#placeIdInput') as HTMLInputElement
    let validator = (element: HTMLInputElement, minSize: number = 1) => (e: Event) => {
      if (element.value.length < minSize) {
        element.classList.add('is-invalid')
      } else {
        element.classList.remove('is-invalid')
      }
    }
    titleInput.oninput = validator(titleInput)
    descriptionInput.oninput = validator(descriptionInput)
    advertTypeInput.oninput = validator(advertTypeInput)
    placeIdInput.oninput = validator(placeIdInput)
  }

  isLoggedIn(): boolean {
    return localStorage.getItem('jwt') !== null && localStorage.getItem('jwt') !== undefined
  }

  createAdvert() {
    let titleInput = document.querySelector('#titleInput') as HTMLInputElement
    let descriptionInput = document.querySelector('#descriptionInput') as HTMLInputElement
    let advertTypeInput = document.querySelector('#advertTypeInput') as HTMLInputElement
    let placeIdInput = document.querySelector('#placeIdInput') as HTMLInputElement
    if (this.title.length === 0) {
      titleInput.classList.add('is-invalid')
    } else {
      titleInput.classList.remove('is-invalid')
    }
    if (this.description.length === 0) {
      descriptionInput.classList.add('is-invalid');
    } else {
      descriptionInput.classList.remove('is-invalid')
    }
    if (this.advertType.length === 0) {
      advertTypeInput.classList.add('is-invalid');
    } else {
      advertTypeInput.classList.remove('is-invalid')
    }
    if (this.placeId === -1) {
      placeIdInput.classList.add('is-invalid');
    } else {
      placeIdInput.classList.remove('is-invalid')
    }

    this.httpClient.post(Constants.api + 'adverts/create?token=' + localStorage.getItem('jwt'), {
      'advertType': this.advertType,
      'title': this.title,
      'description': this.description,
      'placeId': this.placeSelector.selectedPlace.id
    }, {
      headers: {
        'Content-type': 'text/json; charset=UTF-8'
      }
    }).subscribe(r => this.router.navigate(['adverts']))
  }
}
