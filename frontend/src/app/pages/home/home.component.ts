import { Component } from '@angular/core';
import { PostComponent } from "../../components/post/post.component";
import { NavComponent } from "../../components/nav/nav.component";

@Component({
    selector: 'app-home',
    standalone: true,
    templateUrl: './home.component.html',
    imports: [PostComponent, NavComponent]
})
export class HomeComponent {

}
