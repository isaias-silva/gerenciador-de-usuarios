import { Component, Input, OnInit } from '@angular/core';
import { Iuser } from '../../interfaces/api/user.data.interface';
import { UserService } from '../../services/user/user.service';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { EditUserModalComponent } from '../dialogs/edit-user-modal/edit-user-modal.component';
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
      this.userService.me().subscribe({
        next: (user) => {
          if (user) {
            this.userInfo = user;
          }
        }
      })
    }

  }

  openDialog() {
    const openDialog = this.dialog.open(EditUserModalComponent, {
      width: '80%',
      height: '600px',
      
      data: this.userInfo
    })
    openDialog.afterClosed().subscribe({
      next: (v) => {
        console.log(v)
      }
    })
  }
}
