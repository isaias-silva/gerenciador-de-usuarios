import { Component, Input, OnInit } from '@angular/core';
import { AuthComponent } from '../../components/forms/auth/auth.component';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { RegisterComponent } from "../../components/forms/register/register.component";

@Component({
    selector: 'app-login-and-register',
    standalone: true,
    templateUrl: './login-and-register.component.html',
    imports: [AuthComponent, CommonModule, RegisterComponent]
})
export class LoginAndRegisterComponent implements OnInit {
 ngOnInit(): void {
   
  if(this.router.url=='/login'){
    this.register=false
  }else{
    this.register=true
  }
 }
 constructor(private router:Router){

 }
 register?:boolean;
}
