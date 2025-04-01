import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { FooterComponent } from './components/footer/footer.component';
import { TeamListComponent } from './components/team-list/team-list.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { TrophyFilterPipe } from './pipes/filter.pipe';
import { NeverWinIplPipe } from './pipes/never-win-ipl.pipe';
import { CommonModule } from '@angular/common';
import { PlayersComponent } from './components/players/players.component';
import { AddTeamComponent } from './components/add-team/add-team.component';
import { AddPlayerComponent } from './components/add-player/add-player.component';
import { AboutComponent } from './components/about/about.component';
import { WinChancePipe } from './pipes/win-chance.pipe';
import { TeamHighlightComponent } from './components/team-highlight/team-highlight.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    FooterComponent,
    TeamListComponent,
    TrophyFilterPipe,
    NeverWinIplPipe,
    PlayersComponent,
    AddTeamComponent,
    AddPlayerComponent,
    AboutComponent,
    WinChancePipe,
    TeamHighlightComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
    RouterModule
  ],
  providers: [
    provideClientHydration()
  ],
  
  bootstrap: [AppComponent]
})
export class AppModule { }
