import { Component } from '@angular/core';
import { TeamService } from '../../services/team.service';

@Component({
  selector: 'app-add-team',
  templateUrl: './add-team.component.html',
  styleUrl: './add-team.component.css'
})
export class AddTeamComponent {
  team = { name: '', owner: '', captain: '', homeGround: '', trophies: 0, logo: '' };

  constructor(private teamService: TeamService) {}

  addTeam() {
    this.teamService.addTeam(this.team).subscribe(response => {
      console.log('Team added:', response);
      alert('Team added successfully!');
      this.team = { name: '', owner: '', captain: '', homeGround: '', trophies: 0, logo: '' };
    });
  }
}