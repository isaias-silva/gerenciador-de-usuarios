import { MatButtonModule } from '@angular/material/button';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { Iuser } from '../../../interfaces/api/user.data.interface';
import { ProfileChangerComponent } from "../../profile-changer/profile-changer.component";
import { IupdateUser } from '../../../interfaces/api/user.update.interface';
import { UserService } from '../../../services/user/user.service';
import { InfoComponent } from "../../utils/info/info.component";
import { Observable, delay, map, of } from 'rxjs';
import { CommonModule } from '@angular/common';
import { IerrorRegister } from '../../../interfaces/api/response.error.register';


@Component({
  selector: 'app-edit-user-modal',
  standalone: true,
  templateUrl: './edit-user-modal.component.html',
  styleUrl: './edit-user-modal.component.css',
  imports: [MatFormFieldModule, MatInputModule, FormsModule, ReactiveFormsModule, MatDialogModule, ProfileChangerComponent, InfoComponent, CommonModule]
})
export class EditUserModalComponent implements OnInit {
  constructor(private matDialogRef: MatDialogRef<EditUserModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Iuser, private userService: UserService,
  ) {

  }

  form = new FormGroup({
    name: new FormControl(this.data.name),
    mail: new FormControl(this.data.mail),
    resume: new FormControl(this.data.resume),
    portfolioURL: new FormControl(this.data.portfolioURL),
    githubURL: new FormControl(this.data.githubURL),
    instagramURL: new FormControl(this.data.instagramURL)
  })

  theme?: string | null
  valid: boolean = false

  message?: { status: 'error' | 'success' | 'info', message: string } | null


  submit() {
    if (this.form.invalid) {
      return
    }
    const updateObject = {
      name: this.compare("name"),
      mail: this.compare("mail"),
      resume: this.compare("resume"),
      githubURL: this.compare("githubURL"),
      instagramURL: this.compare("instagramURL"),
      portfolioURL: this.compare("portfolioURL")
    }


    this.save(updateObject)


  }

  showMessage(message: string, status: 'error' | 'info' | 'success') {

    const show = of({ message, status }).pipe(

      map((message) => {
        this.message = message
        return message
      }),

      delay(4000),

      map((message) => {
       
          if(message.status!='error'){
            this.matDialogRef.close({ reload: true, isMailChange: this.data.mail != this.form.controls.mail.value })
        
          }else{
            this.message=undefined
          }
         


      }

      ))
    show.subscribe()
  }
  private compare(key: "name" | "mail" | "githubURL" | "instagramURL" | "portfolioURL" | "resume") {
    if (this.data[key] != this.form.controls[key].value && this.form.controls[key].value) {
      this.valid = true
      return this.form.controls[key].value
    } else {
      return null
    }
  }

  save(updateObject: IupdateUser) {


    if (!this.valid) {
      return
    }
    if (updateObject) {
      this.userService.update(updateObject).subscribe({
        next: (res) => {
          this.showMessage(res.message, 'success')

        },
        error:(err:IerrorRegister)=>{
          console.log(err)
          if(err?.error?.errors){
            this.showMessage(err.error?.errors[0], 'error')
         
          }
         
        }
      })

    }
  }
  ngOnInit(): void {
    this.theme = localStorage.getItem('theme')
  }
}


