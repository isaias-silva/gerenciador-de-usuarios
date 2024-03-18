import { Component, OnInit } from '@angular/core';
import { Iuser } from '../../interfaces/api/user.data.interface';
import { UserService } from '../../services/user/user.service';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../services/user/auth.service';


@Component({
  selector: 'app-nav',
  standalone: true,
  imports: [CommonModule,MatIconModule,RouterModule],
  templateUrl: './nav.component.html',

})
export class NavComponent implements OnInit {
  userInfo?: Iuser

  constructor(private userService: UserService,private authService:AuthService) { }
  ngOnInit() {
    this.userService.me().subscribe({
      next:(user)=>{
        if(user){
          this.userInfo=user;
        }
      }
    })
  }
  logout(){
    this.authService.logout()
  }

}
