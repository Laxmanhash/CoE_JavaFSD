import { Component, OnInit, Renderer2 } from '@angular/core';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.css'
})
export class FooterComponent implements OnInit {
  typingText: string = "Created by laxman";
  displayedText: string = "";
  index: number = 0;

  constructor() {}

  ngOnInit(): void {
    this.typeText();
  }

  typeText() {
    if (this.index < this.typingText.length) {
      this.displayedText += this.typingText.charAt(this.index);
      this.index++;
      setTimeout(() => this.typeText(), 100);  
    }
  }
}
