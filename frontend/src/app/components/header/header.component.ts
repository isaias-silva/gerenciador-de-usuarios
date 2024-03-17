import { Component, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon'
import { UserService } from '../../services/user/user.service';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-header',
  standalone: true,
  imports: [MatIconModule,CommonModule],
  templateUrl: './header.component.html'
})
export class HeaderComponent implements OnInit {
  visible: boolean = true;
  constructor(private userService: UserService) { }
  ngOnInit(): void {
    this.visible = this.userService.getToken() != null
  }
}
