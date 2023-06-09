import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {RegistrationComponent} from "./registration/registration.component";
import {LoginComponent} from "./login/login.component";
import {AccountComponent} from "./account/account.component";
import {CreateAnimalAdvertComponent} from "./create-animal-advert/create-animal-advert.component";
import {AnimalAdvertsComponent} from "./animal-adverts/animal-adverts.component";
import {AnimalAdvertComponent} from "./animal-advert/animal-advert.component";
import {AdvertsComponent} from "./adverts/adverts.component";
import {ShelterComponent} from "./shelter/shelter.component";
import {CreateAdvertComponent} from "./create-advert/create-advert.component";
import {AdminPanelComponent} from "./admin-panel/admin-panel.component";
import {
  CanActivateAdminPanelGuard, CanActivateIfAuthenticated,
  CanActivateModeratorPanelGuard,
  CanActivateShelterModeratorPanelGuard
} from "../shared/activator";
import {ModeratorToolsComponent} from "./moderator-tools/moderator-tools.component";
import {PreviewComponent} from "./preview/preview.component";
import {SheltersComponent} from "./shelters/shelters.component";
import {EditShelterComponent} from "./edit-shelter/edit-shelter.component";
import {AdvertComponent} from "./advert/advert.component";
import {ChangeInfoComponent} from "./change-info/change-info.component";
import {UpdatePasswordComponent} from "./update-password/update-password.component";

const routes: Routes = [
  {path: '', component: PreviewComponent},
  {path: 'registration', component: RegistrationComponent},
  {path: 'login', component: LoginComponent},
  {path: 'account', component: AccountComponent, canActivate: [CanActivateIfAuthenticated]},
  {path: 'createAnimalAdvert', component: CreateAnimalAdvertComponent, canActivate: [CanActivateIfAuthenticated]},
  {path: 'createAdvert', component: CreateAdvertComponent, canActivate: [CanActivateIfAuthenticated]},
  {path: 'animalAdverts', component: AnimalAdvertsComponent},
  {path: 'animalAdvert', component: AnimalAdvertComponent},
  {path: 'adverts', component: AdvertsComponent},
  {path: 'advert', component: AdvertComponent},
  {path: 'shelter', component: ShelterComponent},
  {path: 'admin', component: AdminPanelComponent, canActivate: [CanActivateAdminPanelGuard]},
  {path: 'moderator', component: ModeratorToolsComponent, canActivate: [CanActivateModeratorPanelGuard]},
  {path: 'shelters', component: SheltersComponent},
  {path: 'editShelter', component: EditShelterComponent, canActivate: [CanActivateShelterModeratorPanelGuard]},
  {path: 'advert', component: AdvertComponent},
  {path: 'editAccount', component: ChangeInfoComponent, canActivate: [CanActivateIfAuthenticated]},
  {path: 'updateLoginAndPassword', component: UpdatePasswordComponent, canActivate: [CanActivateIfAuthenticated]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
