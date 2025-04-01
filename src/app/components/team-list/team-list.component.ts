import { Component, NgModule, OnInit, Renderer2 } from '@angular/core';
import { TeamService } from '../../services/team.service';
import { CommonModule } from '@angular/common';
import { TeamDetailsComponent } from '../team-details/team-details.component';

@Component({
  selector: 'app-team-list',
  templateUrl: './team-list.component.html',
  styleUrls: ['./team-list.component.css']
})
export class TeamListComponent implements OnInit {
  teams: any[] = [];
  minTrophies: any;
  showNeverWinTeams: boolean = false;

  constructor(private teamService: TeamService, private renderer: Renderer2) { }

  ngOnInit() {
    this.teamService.getTeams().subscribe(data => {
      this.teams = data;
    });
  }

  @NgModule({
    declarations: [TeamDetailsComponent],
    imports: [CommonModule] 
  })

  toggleNeverWinTeams() {
    this.showNeverWinTeams = !this.showNeverWinTeams;

    if (this.showNeverWinTeams) {
      this.showCrackerEffect();
      setTimeout(() => {
        alert("🔥 Welcome to the RCB Club! 🔥");
      }, 1000);
    }
  }

  showCrackerEffect() {
    const body = document.body;
    
    for (let i = 0; i < 20; i++) {
      let cracker = this.renderer.createElement('div');
      this.renderer.addClass(cracker, 'cracker');
      this.renderer.appendChild(body, cracker);

      setTimeout(() => {
        this.renderer.removeChild(body, cracker);
      }, 2000);
    }
  }
}
