import {SafeResourceUrl} from "@angular/platform-browser";

export interface User {
  id: number
  name: string
  surname: string
  phoneNumber: string,
  email: string
}

export interface Place {
  id: number,
  country: string
  region: string
  city: string,
  district: string
}

export interface Shelter {
  id: number
  name: string
  place: Place
}

export interface AnimalAdvert {
  advertId: number
  userId: number
  title: string
  description: string,
  animalType: string,
  creationDate: string,
  place: Place,
  birthDate: string,
  sex: symbol
  isCastrated: boolean
  shelterId: number
  shelterName: string
}

export interface Advert {
  id: number
  title: string,
  description: string,
  ownerId: number,
  advertType: string,
  place: Place,
  creationDate: string
}

export interface Photo {
  base64content: string;
}

export class AnimalAdvertWithPhoto {
  constructor(public advert: AnimalAdvert, public photo: SafeResourceUrl) {
  }
}

export class Roles {
  public static readonly User: string = 'USER'
  public static readonly Moderator: string = 'MODERATOR'
  public static readonly ShelterAdministrator: string = 'SHELTER_ADMINISTRATOR'
  public static readonly Administrator: string = 'ADMINISTRATOR'
}
