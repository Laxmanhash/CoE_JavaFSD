import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-team-highlight',
  templateUrl: './team-highlight.component.html',
  styleUrls: ['./team-highlight.component.css']
})
export class TeamHighlightComponent implements OnInit {
  
  @Input() teams: any[] = []; 
  processedTeams: any[] = []; 

  ngOnInit(): void {
    if (this.teams.length > 0) {
      this.processTeams();
    }
  }

  processTeams() {
    this.processedTeams = this.teams.map(team => {
      return {
        ...team,
        message: this.generateTeamMessage(team),
        winningProbability: this.calculateWinningProbability(team.trophies)
      };
    });
  }

  generateTeamMessage(team: any): string {
    if (team.trophies > 3) {
      return `🔥 ${team.name} is an IPL Giant with ${team.trophies} trophies!`;
    } else if (team.trophies === 0) {
      return `😢 ${team.name} is still waiting for its first IPL trophy!`;
    } else {
      return `✨ ${team.name} is a strong competitor with ${team.trophies} trophies.`;
    }
  }

  calculateWinningProbability(trophies: number): number {
    let probability = (trophies * 15) + 9; 
    return probability > 95 ? 95 : probability; 
  }
}
