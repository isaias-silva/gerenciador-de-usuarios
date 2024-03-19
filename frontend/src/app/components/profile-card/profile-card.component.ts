import { Component, Input, OnInit } from '@angular/core';
import { Iuser } from '../../interfaces/api/user.data.interface';
import { UserService } from '../../services/user/user.service';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { EditUserModalComponent } from '../dialogs/edit-user-modal/edit-user-modal.component';
import { BehaviorSubject } from 'rxjs';
import { IupdateUser } from '../../interfaces/api/user.update.interface';
@Component({
  selector: 'app-profile-card',
  standalone: true,
  imports: [CommonModule, MatIconModule, MatDialogModule],
  templateUrl: './profile-card.component.html'
})
export class ProfileCardComponent implements OnInit {
  @Input() me: boolean = true;
  @Input() id?: string;

  userInfo?: Iuser

  constructor(private userService: UserService, public dialog: MatDialog) { }


  ngOnInit() {
    if (this.me) {
      this.updateUserInfo()
    }

  }

  updateUserInfo() {
    this.userService.me()
    const { userSubject } = this.userService
    userSubject.subscribe(v => {
      console.log(v)
      if (v) {
        this.userInfo = v
      }
    })
  }
  openDialog() {
    const openDialog = this.dialog.open(EditUserModalComponent, {
      width: '60%',
      height: '550px',

      data: this.userInfo
    })
    openDialog.afterClosed().subscribe({
      next: () => {
      
       
        this.updateUserInfo()
      }
    })
  }
}
