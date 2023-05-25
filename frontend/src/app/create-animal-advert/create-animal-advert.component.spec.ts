import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateAnimalAdvertComponent } from './create-animal-advert.component';

describe('CreateAnimalAdvertComponent', () => {
  let component: CreateAnimalAdvertComponent;
  let fixture: ComponentFixture<CreateAnimalAdvertComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateAnimalAdvertComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateAnimalAdvertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
