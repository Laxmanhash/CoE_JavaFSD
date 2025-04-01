import { Component } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { PlayerService } from '../../services/player.service';

@Component({
  selector: 'app-add-player',
  templateUrl: './add-player.component.html',
  styleUrls: ['./add-player.component.css']
})
export class AddPlayerComponent {
  playerForm = new FormGroup({
    name: new FormControl(''),
    team: new FormControl(''),
    role: new FormControl('Batsman'),
    matches: new FormControl(0),
    runs: new FormControl(0),
    wickets: new FormControl(0),
    image: new FormControl('')
  });

  constructor(private playerService: PlayerService) {}

  addPlayer() {
    this.playerService.addPlayer(this.playerForm.value).subscribe(response => {
      console.log('Player added:', response);
      alert('Player added successfully!');
      this.playerForm.reset();
    });
  }
}