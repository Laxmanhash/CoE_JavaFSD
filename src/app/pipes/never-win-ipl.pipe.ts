import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'neverWinIpl'
})
export class NeverWinIplPipe implements PipeTransform {
  transform(teams: any[]): any[] {
    return teams.filter(team => team.trophies === 0);
  }
}