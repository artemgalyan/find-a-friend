import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
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
import {CanActivateAdminPanelGuard, CanActivateModeratorPanelGuard} from "../shared/activator";
import {ModeratorToolsComponent} from "./moderator-tools/moderator-tools.component";
import {SheltersComponent} from "./shelters/shelters.component";

const routes: Routes = [
  {path: 'registration', component: RegistrationComponent},
  {path: 'login', component: LoginComponent},
  {path: 'account', component: AccountComponent},
  {path: 'createAnimalAdvert', component: CreateAnimalAdvertComponent},
  {path: 'createAdvert', component: CreateAdvertComponent},
  {path: 'animalAdverts', component: AnimalAdvertsComponent},
  {path: 'animalAdvert', component: AnimalAdvertComponent},
  {path: 'adverts', component: AdvertsComponent},
  {path: 'shelter', component: ShelterComponent},
  {path: 'admin', component: AdminPanelComponent, canActivate: [CanActivateAdminPanelGuard] },
  {path: 'moderator', component: ModeratorToolsComponent, canActivate: [CanActivateModeratorPanelGuard]},
  {path: 'shelters', component: SheltersComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
