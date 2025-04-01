import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'winChance'
})
export class WinChancePipe implements PipeTransform {
  transform(trophies: number): number {
    return (trophies * 15) + 9; 
  }
}
