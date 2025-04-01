import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TeamService } from '../../services/team.service';

@Component({
  selector: 'app-team-details', 
  templateUrl: './team-details.component.html',
  styleUrls: ['./team-details.component.css']
})
export class TeamDetailsComponent implements OnInit {
  team: any;

  constructor(private route: ActivatedRoute, private teamService: TeamService) { }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    this.teamService.getTeamById(id).subscribe(data => {
      this.team = data;
    });
  }
}
