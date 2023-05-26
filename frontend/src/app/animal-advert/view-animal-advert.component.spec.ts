import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAnimalAdvertComponent } from './view-animal-advert.component';

describe('AnimalAdvertComponent', () => {
  let component: ViewAnimalAdvertComponent;
  let fixture: ComponentFixture<ViewAnimalAdvertComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewAnimalAdvertComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewAnimalAdvertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
