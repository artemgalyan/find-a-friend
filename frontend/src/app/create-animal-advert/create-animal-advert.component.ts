import {Component, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Constants} from "../constants";
import {PlacePickerComponent} from "../placepicker/place-picker.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-create-animal-advert',
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
  }

  isLoggedIn() : boolean {
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
    }).subscribe(r => console.log(r))
  }

  getSex(s: string) : string {
    if (s === 'F') {
      return 'Ж';
    }
    return 'М';
  }
}
