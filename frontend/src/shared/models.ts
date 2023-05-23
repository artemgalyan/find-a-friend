import {PlaceholderMapper} from "@angular/compiler/src/i18n/serializers/serializer";

interface User {
  id: number
  name: string
  surname: string
  phoneNumber: string,
  email: string
}

interface Place {
  id: number,
  country: string
  region: string
  city: string,
  district: string
}

interface Shelter {
  id: number
  name: string
  place: Place
}

interface AnimalAdvert {
  advertId: number
  userId: number
  title: string
  description: string,
  animalType: string,
  creationDate: Date,
  place: Place,
  birthDate: Date,
  sex: symbol,
  isCastrated: boolean
}

interface Advert {
  id: number
  title: string,
  description: string,
  ownerId: number
}
