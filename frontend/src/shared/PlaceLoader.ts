import {PlaceService} from "./PlaceService";
import {Place} from "./models";

export class PlaceLoader {
  private readonly places: any = {};
  private readonly emptyPlace: Place = {
    id: -1,
    country: '',
    region: '',
    city: '',
    district: ''
  }

  constructor(private placeService: PlaceService) {
  }

  public getPlace(placeId?: number) {
    if (placeId === null || placeId === undefined) {
      return this.emptyPlace;
}
    if (!this.places.hasOwnProperty(placeId)) {
      this.places[placeId] = this.emptyPlace;
      this.placeService.getPlace(placeId)
        .subscribe(r => this.places[placeId] = r);
    }
    return this.places[placeId];
  }
}
