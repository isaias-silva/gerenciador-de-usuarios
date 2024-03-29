import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { UserService } from '../../services/user/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { delay, map, of } from 'rxjs';
import { InfoComponent } from "../../components/utils/info/info.component";

@Component({
    selector: 'app-verify',
    standalone: true,
    templateUrl: './verify.component.html',
    imports: [ReactiveFormsModule, CommonModule, MatProgressSpinnerModule, InfoComponent]
})
export class VerifyComponent implements OnInit {

  constructor(private userService: UserService, private router: Router, private route: ActivatedRoute) {

  }
  ngOnInit(): void {
    const stringNormal = atob(this.route.snapshot.params["message"])
    const words = stringNormal.split('+')
    this.message = words[0]

    this.opt = words[words.length - 1] == 'mchange' ? 'changemail' : 'validatemail'
  }

  form = new FormGroup({
    code: new FormControl<string | null>(null, [Validators.required])
  })
  load: boolean = false
  opt: 'changemail' | 'validatemail' = 'validatemail'
  error?: string;
  message?: string;

  messageDialog?: { status: 'error' | 'success' | 'info', message: string } | null


  sendNewCode() {
    this.userService.sendNewCode().subscribe(
      {
        next: (message) => {
          this.showModal(message.message, 'info')
        }
      }
    )
  }
  showModal(message: string, status: 'error' | 'success' | 'info') {
    const show = of({ message, status }).pipe(

      map((message) => {
        this.messageDialog = message
        return message
      }),

      delay(4000),

      map((message) => {
        this.messageDialog = undefined
      }
      ))

    show.subscribe()
  }
  submit() {
    this.error = undefined
    const { code } = this.form.controls
    if (this.form.invalid) {
      this.error = "digite o código que recebeu por e-mail"
      return
    } else if (code.value) {
      this.load = true
      alert(this.opt)
      if (this.opt == 'validatemail') {

        this.userService.validate(code.value).subscribe({
          next: (res) => {
            this.load = false
            this.router.navigate(['/'])

          },
          error: (err) => {

            this.error = err.error.message

            this.load = false
          }
        })
      } else {
        this.userService.confirmChangeMail(code.value).subscribe({
          next: (res) => {
            this.load = false
            this.router.navigate(['/'])

          },
          error: (err) => {

            this.error = err.error.message

            this.load = false
          }
        })
      }

    }
  }
}
