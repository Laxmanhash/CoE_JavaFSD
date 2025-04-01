import { Component, OnInit } from '@angular/core';
import { PlayerService } from '../../services/player.service';

@Component({
  selector: 'app-players',
  templateUrl: './players.component.html',
  styleUrl: './players.component.css'
})
export class PlayersComponent implements OnInit {
  players: any[] = [];

  constructor(private playerService: PlayerService) { }

  ngOnInit() {
    this.playerService.getPlayers().subscribe(data => {
      this.players = data;
    });
  }
}