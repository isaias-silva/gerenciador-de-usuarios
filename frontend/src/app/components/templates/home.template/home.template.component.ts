import { Component } from '@angular/core';
import { HeaderComponent } from "../../header/header.component";
import { RouterModule } from '@angular/router';
import { NavComponent } from "../../nav/nav.component";

@Component({
    selector: 'app-home',
    standalone: true,
    templateUrl: './home.template.component.html',
    imports: [HeaderComponent, RouterModule, NavComponent]
})
export class HomeTemplateComponent {

}
