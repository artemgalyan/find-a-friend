import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css'],

})
export class AdminPanelComponent implements OnInit {
  userId: number = 0
  advertId: number = 0
  animalAdvertId: number = 0
  shelterIdToDelete: number = 0
  constructor() { }

  ngOnInit(): void {
  }

}
