import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnimalAdvertComponent } from './animal-advert.component';

describe('AnimalAdvertComponent', () => {
  let component: AnimalAdvertComponent;
  let fixture: ComponentFixture<AnimalAdvertComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AnimalAdvertComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AnimalAdvertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
