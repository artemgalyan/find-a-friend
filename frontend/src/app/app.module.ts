import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {RegistrationComponent} from './registration/registration.component';
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {LoginComponent} from './login/login.component';
import {AccountComponent} from './account/account.component';
import {CreateAnimalAdvertComponent} from './create-animal-advert/create-animal-advert.component';
import {PlacePickerComponent} from './placepicker/place-picker.component';
import {AnimalAdvertsComponent} from './animal-adverts/animal-adverts.component';
import {ViewAnimalAdvertComponent} from './view-animal-advert/view-animal-advert.component';
import {AnimalAdvertComponent} from './animal-advert/animal-advert.component';
import {HeaderComponent} from './header/header.component';
import {AdvertsComponent} from './adverts/adverts.component';
import {ShelterComponent} from './shelter/shelter.component';
import {CreateAdvertComponent} from './create-advert/create-advert.component';
import {
  CanActivateAdminPanelGuard, CanActivateIfAuthenticated,
  CanActivateModeratorPanelGuard,
  CanActivateShelterModeratorPanelGuard
} from "../shared/activator";
import {AdminPanelComponent} from "./admin-panel/admin-panel.component";
import {ModeratorToolsComponent} from './moderator-tools/moderator-tools.component';
import {AdvertComponent} from './advert/advert.component';
import {PreviewComponent} from './preview/preview.component';
import {SheltersComponent} from './shelters/shelters.component';
import {EditShelterComponent} from './edit-shelter/edit-shelter.component';
import { ChangeInfoComponent } from './change-info/change-info.component';
import { UpdatePasswordComponent } from './update-password/update-password.component';

@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    LoginComponent,
    AccountComponent,
    CreateAnimalAdvertComponent,
    PlacePickerComponent,
    AnimalAdvertsComponent,
    ViewAnimalAdvertComponent,
    AnimalAdvertComponent,
    HeaderComponent,
    AdvertsComponent,
    ShelterComponent,
    CreateAdvertComponent,
    AdminPanelComponent,
    ModeratorToolsComponent,
    PreviewComponent,
    ModeratorToolsComponent,
    SheltersComponent,
    EditShelterComponent,
    AdvertComponent,
    ChangeInfoComponent,
    UpdatePasswordComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [CanActivateAdminPanelGuard, CanActivateModeratorPanelGuard, CanActivateShelterModeratorPanelGuard, CanActivateIfAuthenticated],
  bootstrap: [AppComponent]
})
export class AppModule {
}
