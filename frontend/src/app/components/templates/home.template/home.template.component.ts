import { Component } from '@angular/core';
import { HeaderComponent } from "../../header/header.component";
import { RouterModule } from '@angular/router';


@Component({
    selector: 'app-home',
    standalone: true,
    templateUrl: './home.template.component.html',
    imports: [HeaderComponent, RouterModule]
})
export class HomeTemplateComponent {

}
