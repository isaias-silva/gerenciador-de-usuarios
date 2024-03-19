import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-profile-changer',
  standalone: true,
  imports: [],
  templateUrl: './profile-changer.component.html',
 
})
export class ProfileChangerComponent {
@Input() preview?:string


changeProfile(event:any){

if(event.target){
 
  if (event.target.files[0].type.includes("image") == false) {
    return
  } else {
    this.preview = URL.createObjectURL(event.target.files[0]);
  
  }
}

}
}
