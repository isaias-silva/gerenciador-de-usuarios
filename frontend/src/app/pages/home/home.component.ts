import { Component } from '@angular/core';
import { PostComponent } from "../../components/post/post.component";

@Component({
    selector: 'app-home',
    standalone: true,
    templateUrl: './home.component.html',
  
    imports: [PostComponent]
})
export class HomeComponent {

}
