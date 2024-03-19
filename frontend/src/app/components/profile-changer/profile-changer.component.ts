import { Component, Input } from '@angular/core';
import { UserService } from '../../services/user/user.service';

@Component({
  selector: 'app-profile-changer',
  standalone: true,
  imports: [],
  templateUrl: './profile-changer.component.html',
 
})
export class ProfileChangerComponent {
@Input() preview?:string

constructor(private userService:UserService){}

changeProfile(event:any){

if(event.target){
 
  if (event.target.files[0].type.includes("image") == false) {
    return
  } else {
    
    this.preview = URL.createObjectURL(event.target.files[0]);
    this.userService.updateProfile(event.target.files[0]).subscribe({
      next:(response)=>{
        
        alert(response.message)
      },
    error:(err)=>{
      console.log(err)
    }
    })
  }
}

}
}
