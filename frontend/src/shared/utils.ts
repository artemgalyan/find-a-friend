import {Place} from "./models";

export enum LoadingState {
  Loading,
  Success,
  Error
}

export function dateToString(d?: string): string {
  if (d === null) {
    return ""
  }
  let date = new Date(Number(d as string))
  return date.getDate().toString().padStart(2, '0') + '.' + (date.getMonth() + 1).toString().padStart(2, '0') + '.' + date.getFullYear()
}

export function placeToString(p: Place): string {
  return p.city + ', ' + p.district;
}
