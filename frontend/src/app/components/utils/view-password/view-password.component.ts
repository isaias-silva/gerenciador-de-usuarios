import { Component, EventEmitter, Output } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'view-password',
  standalone: true,
  imports: [MatIconModule],
  templateUrl: './view-password.component.html',
})
export class ViewPasswordComponent {
 visible:boolean=false
  @Output() visibleChange: EventEmitter<boolean> = new EventEmitter();

changeVisible(){
  this.visible=!this.visible
  this.visibleChange.emit(this.visible)
}
}
