import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Observable} from "rxjs";
import {Roles} from "./models";
import {Injectable} from "@angular/core";

@Injectable()
export class CanActivateAdminPanelGuard implements CanActivate {
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return localStorage.getItem('role') === Roles.Administrator;
  }
}

@Injectable()
export class CanActivateModeratorPanelGuard implements CanActivate {
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const role = localStorage.getItem('role');
    return role === Roles.Administrator || role === Roles.Moderator;
  }

}

@Injectable()
export class CanActivateShelterModeratorPanelGuard implements CanActivate {
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const role = localStorage.getItem('role');
    return role === Roles.Administrator || role === Roles.Moderator || role === Roles.ShelterAdministrator;
  }

}

@Injectable()
export class CanActivateIfAuthenticated implements CanActivate {
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return localStorage.getItem('role') !== null;
  }

}
