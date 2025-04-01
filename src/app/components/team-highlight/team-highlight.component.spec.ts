import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamHighlightComponent } from './team-highlight.component';

describe('TeamHighlightComponent', () => {
  let component: TeamHighlightComponent;
  let fixture: ComponentFixture<TeamHighlightComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TeamHighlightComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TeamHighlightComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
