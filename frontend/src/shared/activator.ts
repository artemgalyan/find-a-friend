import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Observable} from "rxjs";
import {Roles} from "./models";
import {Injectable} from "@angular/core";

@Injectable()
export class CanActivateAdminPanelGuard implements CanActivate {
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    console.log(localStorage.getItem('role'))
    return localStorage.getItem('role') === Roles.Administrator;
  }

}
