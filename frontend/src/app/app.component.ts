import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from "./components/header/header.component";
import { ThemeControlComponent } from "./components/theme-control/theme-control.component";

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  imports: [RouterOutlet, HeaderComponent, ThemeControlComponent]
})
export class AppComponent {
  title = 'frontend';
  globalMode: 'dark' | 'light' = 'light'
  onChangeTheme(dark:boolean) {
   this.globalMode=dark?'dark':'light'
  }

}
