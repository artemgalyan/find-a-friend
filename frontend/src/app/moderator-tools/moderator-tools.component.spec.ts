import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModeratorToolsComponent } from './moderator-tools.component';

describe('ModeratorToolsComponent', () => {
  let component: ModeratorToolsComponent;
  let fixture: ComponentFixture<ModeratorToolsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModeratorToolsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ModeratorToolsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
