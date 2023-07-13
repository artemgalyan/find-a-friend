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
  placeId: number,
  address: string,
  website: string
}

export interface AnimalAdvert {
  id: number
  ownerId: number
  title: string
  description: string,
  animalType: string,
  creationDate: string,
  placeId: number,
  birthDate: string,
  sex: AnimalSex
  isCastrated: boolean
  shelterId: number
  shelterName: string
}

export enum AnimalSex {
  Male = 0,
  Female
}

export interface Advert {
  id: number
  title: string,
  description: string,
  ownerId: number,
  advertType: AdvertType,
  placeId: number,
  creationDate: string
}

export enum AdvertType {
  Sitter = 0,
  Volunteer
}

export interface Photo {
  base64Content: string;
}

export const advertTypes: any = {
  0: 'Ситтер',
  1: 'Волонтёр'
}

export class AnimalAdvertWithPhoto {
  constructor(public advert: AnimalAdvert, public photo: SafeResourceUrl) {
  }
}

export class Roles {
  public static readonly User: string = 'User'
  public static readonly Moderator: string = 'Moderator'
  public static readonly ShelterAdministrator: string = 'ShelterAdministrator'
  public static readonly Administrator: string = 'Administrator'
  public static readonly NumberToRole = {
    '0': Roles.User,
    '1': Roles.Moderator,
    '2': Roles.Administrator,
    '3': Roles.ShelterAdministrator
  };
}
