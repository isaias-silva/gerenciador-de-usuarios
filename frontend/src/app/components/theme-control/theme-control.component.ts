import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';

@Component({
  selector: 'app-theme-control',
  standalone: true,
  imports: [MatIconModule, MatSlideToggleModule],
  templateUrl: './theme-control.component.html',
})
export class ThemeControlComponent implements OnInit{
  @Output() themeChange:EventEmitter<boolean>=new EventEmitter();
 
  darkMode: boolean = false;

 
  ngOnInit(): void {
    const theme = localStorage.getItem('theme')
    if (theme) {
      this.darkMode = true
    }
    this.themeChange.emit(this.darkMode)
  }


  changeMode = (ev: Event | any) => {
    if (ev.target) {

      const { target } = ev

      this.darkMode = target.checked

      if (target.checked) {
        localStorage.setItem("theme", "dark")
      } else {
        localStorage.removeItem("theme")
      }
      this.themeChange.emit(this.darkMode)
    }
  }

}
