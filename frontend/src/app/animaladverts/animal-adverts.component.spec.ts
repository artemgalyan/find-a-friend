import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnimalAdvertsComponent } from './animal-adverts.component';

describe('AnimaladvertsComponent', () => {
  let component: AnimalAdvertsComponent;
  let fixture: ComponentFixture<AnimalAdvertsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AnimalAdvertsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AnimalAdvertsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
