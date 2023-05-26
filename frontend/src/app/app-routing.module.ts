import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {RegistrationComponent} from "./registration/registration.component";
import {LoginComponent} from "./login/login.component";
import {AccountComponent} from "./account/account.component";
import {CreateAnimalAdvertComponent} from "./create-animal-advert/create-animal-advert.component";
import {AnimalAdvertsComponent} from "./animaladverts/animal-adverts.component";
import {AnimalAdvertComponent} from "./animaladvert/animal-advert.component";

const routes: Routes = [
  {path: 'registration', component: RegistrationComponent},
  {path: 'login', component: LoginComponent},
  {path: 'account', component: AccountComponent},
  {path: 'createAnimalAdvert', component: CreateAnimalAdvertComponent},
  {path: 'animalAdverts', component: AnimalAdvertsComponent},
  {path: 'animalAdvert', component: AnimalAdvertComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
