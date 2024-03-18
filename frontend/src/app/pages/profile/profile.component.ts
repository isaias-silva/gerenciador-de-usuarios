import { Component } from '@angular/core';
import { ProfileCardComponent } from "../../components/profile-card/profile-card.component";

@Component({
    selector: 'app-profile',
    standalone: true,
    templateUrl: './profile.component.html',
    imports: [ProfileCardComponent]
})
export class ProfileComponent {

}
