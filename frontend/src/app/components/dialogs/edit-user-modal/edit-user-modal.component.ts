import { MatButtonModule } from '@angular/material/button';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { Iuser } from '../../../interfaces/api/user.data.interface';
import { ProfileChangerComponent } from "../../profile-changer/profile-changer.component";
import { IupdateUser } from '../../../interfaces/api/user.update.interface';


@Component({
  selector: 'app-edit-user-modal',
  standalone: true,
  templateUrl: './edit-user-modal.component.html',
  styleUrl: './edit-user-modal.component.css',
  imports: [MatFormFieldModule, MatInputModule, FormsModule, ReactiveFormsModule, MatDialogModule, ProfileChangerComponent]
})
export class EditUserModalComponent implements OnInit {
  constructor(matDialogRef: MatDialogRef<EditUserModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Iuser) {

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


  submit() {
    if (this.form.invalid) {
      return
    }
    const updateObject: IupdateUser = {
      name:this.compare("name"),
      mail:this.compare("mail"),
      resume: this.compare("resume"),
      githubURL:this.compare("githubURL"),
      instagramURL:this.compare("instagramURL"),
      portfolioURL:this.compare("portfolioURL")
    }
    console.log(updateObject)


  }

  private compare(key: "name" | "mail" | "githubURL" | "instagramURL" | "portfolioURL" | "resume") {
    if (this.data[key] != this.form.controls[key].value && this.form.controls[key].value) {
      return this.form.controls[key].value
    }else{
      return null
    }
  }
  ngOnInit(): void {
    this.theme = localStorage.getItem('theme')
  }
}
