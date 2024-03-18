import { Component } from '@angular/core';
import { HeaderComponent } from "../../components/header/header.component";
import { RouterModule } from '@angular/router';
import { NavComponent } from "../../components/nav/nav.component";

@Component({
    selector: 'app-home',
    standalone: true,
    templateUrl: './home.component.html',
    imports: [HeaderComponent, RouterModule, NavComponent]
})
export class HomeComponent {

}
