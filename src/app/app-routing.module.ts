import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TeamListComponent } from './components/team-list/team-list.component';
import { TeamDetailsComponent } from './components/team-details/team-details.component';
import { PlayersComponent } from './components/players/players.component';
import { AddTeamComponent } from './components/add-team/add-team.component';
import { AddPlayerComponent } from './components/add-player/add-player.component';
import { AboutComponent } from './components/about/about.component';

const routes: Routes = [
  { path: '', redirectTo: '/teams', pathMatch: 'full' },
  { path: 'teams', component: TeamListComponent },
  { path: 'team/:id', component: TeamDetailsComponent },
  { path: 'players', component: PlayersComponent },
  { path: 'add-team', component: AddTeamComponent }, // Template Form
  { path: 'add-player', component: AddPlayerComponent }, // Reactive Form
  { path: 'about', component: AboutComponent}
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
