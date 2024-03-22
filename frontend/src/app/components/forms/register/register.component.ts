import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { IbodyRegister } from '../../../interfaces/api/body.register.interface';
import { IformUser } from '../../../interfaces/components/form.user.interface';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, RouterModule,ReactiveFormsModule],
  templateUrl: './register.component.html',

})
export class RegisterComponent implements IformUser {
  errors: string[]=[];
  errorClasses: string[]=[];
  
  form = new FormGroup({ 
    name: new FormControl<string | null>(null),
    mail: new FormControl<string | null>(null),
    password: new FormControl<string | null>(null),
    passwordRepite: new FormControl<string | null>(null)
  })

  submitForm() {
    
  }
  drawErrors(){

  }
  clearErrors(){
    
  }

}
