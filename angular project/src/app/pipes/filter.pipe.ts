import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filter'
})
export class TrophyFilterPipe implements PipeTransform {
  transform(teams: any[], minTrophies: number): any[] {
    if (!teams || minTrophies === undefined) {
      return teams;
    }
    return teams.filter(team => team.trophies >= minTrophies);
  }
}
