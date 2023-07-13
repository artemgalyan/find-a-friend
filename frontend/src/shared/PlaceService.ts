import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Place} from "./models";
import {Constants} from "../app/constants";
import {Injectable} from "@angular/core";

@Injectable()
export class PlaceService {
  constructor(private httpClient: HttpClient) {
  }

  public getPlace(id: number) : Observable<Place> {
    return this.httpClient.get<Place>(Constants.api + '/places/getById?id=' + id);
  }
}
