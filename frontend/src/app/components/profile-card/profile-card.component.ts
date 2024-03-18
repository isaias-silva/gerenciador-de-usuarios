import { Component, Input, OnInit } from '@angular/core';
import { Iuser } from '../../interfaces/api/user.data.interface';
import { UserService } from '../../services/user/user.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-profile-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './profile-card.component.html'
})
export class ProfileCardComponent implements OnInit {
  @Input() me:boolean=true;
  @Input() id?:string;
  
  userInfo?: Iuser

  constructor(private userService: UserService) { }
  ngOnInit() {
    if(this.me){
      this.userService.me().subscribe({
        next:(user)=>{
          if(user){
            this.userInfo=user;
          }
        }
      })
    }
    
  }
}
