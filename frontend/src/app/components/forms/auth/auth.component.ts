import { Component } from '@angular/core';
import {MatButtonModule} from '@angular/material/button'
import {MatCheckboxModule} from '@angular/material/checkbox'
@Component({
  selector: 'app-auth',
  standalone: true,
  imports:  [MatButtonModule,MatCheckboxModule],
  templateUrl: './auth.component.html',

})
export class AuthComponent {

}
