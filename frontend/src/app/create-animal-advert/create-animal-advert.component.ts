import {Component, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Constants} from "../constants";
import {PlacePickerComponent} from "../placepicker/place-picker.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-create-view-animal-advert',
  templateUrl: './create-animal-advert.component.html',
  styleUrls: ['./create-animal-advert.component.css'],
  providers: [PlacePickerComponent]
})
export class CreateAnimalAdvertComponent implements OnInit {
  images: string[] = []; // base64 strings
  title: string = '';
  description: string = '';
  animalType: string = '';
  birthdate: Date = null!;
  sex: string = '';
  isCastrated: boolean = false;

  readonly animalTypes: string[] = ['Собака', 'Кот', 'Другое']
  readonly sexes = ['M', 'F'];

  @ViewChild(PlacePickerComponent)
  private placeSelector!: PlacePickerComponent;

  constructor(private httpClient: HttpClient,
              private router: Router) {
  }

  ngOnInit(): void {
    if (!this.isLoggedIn()) {
      this.router.navigate(['login'])
      return
    }
    let titleInput = document.querySelector('#titleInput') as HTMLInputElement
    let descriptionInput = document.querySelector('#descriptionInput') as HTMLInputElement
    let animalTypeInput = document.querySelector('#type-selector') as HTMLInputElement
    let placeIdInput = document.querySelector('#place-picker') as HTMLInputElement
    let birthdateInput = document.querySelector('#startDate') as HTMLInputElement
    let sexInput = document.querySelector('#sex-selector') as HTMLInputElement

    let validator = (element: HTMLInputElement, minSize: number = 1) => (e: Event) => {
      if (element.value.length < minSize) {
        element.classList.add('is-invalid')
      } else {
        element.classList.remove('is-invalid')
      }
    }
    titleInput.oninput = validator(titleInput);
    descriptionInput.oninput = validator(descriptionInput);
    animalTypeInput.oninput = validator(animalTypeInput);
    placeIdInput.oninput = validator(placeIdInput);
    birthdateInput.oninput = validator(birthdateInput);
    sexInput.oninput = validator(sexInput);
  }

  isLoggedIn(): boolean {
    return localStorage.getItem('jwt') !== null && localStorage.getItem('jwt') !== undefined
  }

  onImageUploadClicked(event: any) {
    const files = event.target.files;
    for (let i of files) {
      const reader = new FileReader();
      reader.readAsDataURL(i)
      reader.onload = _ => {
        this.images.push(reader.result as string)
      }
    }
  }

  removeImage(id: number) {
    this.images.splice(id, 1);
  }

  uploadImage() {
    document.getElementById('file-input')!.click();
  }

  createAdvert() {

    let valid = true;
    let titleInput = document.querySelector('#titleInput') as HTMLInputElement
    let descriptionInput = document.querySelector('#descriptionInput') as HTMLInputElement
    let animalTypeInput = document.querySelector('#type-selector') as HTMLInputElement
    let placeIdInput = document.querySelector('#place-picker') as HTMLInputElement
    let birthdateInput = document.querySelector('#startDate') as HTMLInputElement
    let sexInput = document.querySelector('#sex-selector') as HTMLInputElement


    if (this.title.length === 0) {
      titleInput.classList.add('is-invalid')
      valid = false
    } else {
      titleInput.classList.remove('is-invalid')
    }
    if (this.description.length === 0) {
      descriptionInput.classList.add('is-invalid');
      valid = false
    } else {
      descriptionInput.classList.remove('is-invalid')
    }
    if (this.animalType.length === 0) {
      animalTypeInput.classList.add('is-invalid');
      valid = false
    } else {
      animalTypeInput.classList.remove('is-invalid')
    }
    if (this.placeSelector.selectedPlace?.id === undefined || this.placeSelector.selectedPlace?.id === null) {
      placeIdInput.classList.add('is-invalid');
      valid = false
    } else {
      placeIdInput.classList.remove('is-invalid')
    }
    if (this.birthdate === null) {
      birthdateInput.classList.add('is-invalid');
      valid = false
    } else {
      birthdateInput.classList.remove('is-invalid')
    }
    if (this.sex.length === 0) {
      sexInput.classList.add('is-invalid');
      valid = false
    } else {
      sexInput.classList.remove('is-invalid')
    }

    if (!valid) {
      return
    }
    this.httpClient.post(Constants.api + 'animalAdverts/create?token=' + localStorage.getItem('jwt'), {
      'title': this.title,
      'description': this.description,
      'animalType': this.animalType,
      'placeId': this.placeSelector.selectedPlace.id,
      'birthdate': this.birthdate,
      'sex': this.sex,
      'isCastrated': this.isCastrated,
      'photos': this.images
    }, {
      headers: {
        'Content-type': 'text/json; charset=UTF-8'
      }
    }).subscribe(r => this.router.navigate(['animalAdverts']))
  }

  getSex(s: string): string {
    if (s === 'F') {
      return 'Ж';
    }
    return 'М';
  }
}
