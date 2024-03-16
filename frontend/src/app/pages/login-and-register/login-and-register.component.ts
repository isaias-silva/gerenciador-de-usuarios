import { Component } from '@angular/core';
import { AuthComponent } from '../../components/forms/auth/auth.component';

@Component({
  selector: 'app-login-and-register',
  standalone: true,
  imports:[AuthComponent],
  templateUrl: './login-and-register.component.html',
})
export class LoginAndRegisterComponent {

}
