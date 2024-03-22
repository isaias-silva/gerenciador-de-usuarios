import { Component, ElementRef, ViewChild } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { IformUser } from '../../../interfaces/components/form.user.interface';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../services/user/auth.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Router, RouterModule } from '@angular/router';


@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule,RouterModule],
  templateUrl: './auth.component.html',

})
export class AuthComponent implements IformUser {

  constructor(private authService: AuthService, private router: Router) { }

  @ViewChild('mail') inputMail?: ElementRef;
  @ViewChild('pass') inputPassword?: ElementRef;

  form = new FormGroup({
    mail: new FormControl<string | null>(null, [Validators.required, Validators.email]),
    password: new FormControl<string | null>(null, [Validators.required, Validators.pattern(/(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).+$/)])
  })

  errors: string[] = []

  errorClasses: string[] = ['border-red-300', 'border-2'];
  submitForm() {


    if (this.form.invalid) {
      return this.drawErrors()
    }

    this.clearErrors()
    const mail = this.form.controls.mail.value
    const password = this.form.controls.password.value

    if (mail && password)
      this.authService.login({ mail, password }).subscribe(response => {
        this.router.navigate(['/'])
      },
        (errorResponse: HttpErrorResponse) => {
          if (errorResponse.error.message && typeof errorResponse.error.message == 'string') {
            this.errors.push(errorResponse.error.message)
          }
        })

  }
  drawErrors() {
    this.errors = []

    const { mail, password } = this.form.controls;

    if (mail.status == 'INVALID') {
      this.errors.push("e-mail inválido!")
      this.errorClasses.forEach(errorClass => this.inputMail?.nativeElement.classList.add(errorClass))


    }
    if (password.status == 'INVALID') {
      this.errors.push("senha deve conter: letras números e símbolos.")
      this.errorClasses.forEach(errorClass => this.inputPassword?.nativeElement.classList.add(errorClass))


    }
  };
  clearErrors() {
    this.errors = []
    this.errorClasses.forEach(errorClass => {
      this.inputPassword?.nativeElement.classList.remove(errorClass)
      this.inputMail?.nativeElement.classList.remove(errorClass)
    })
  }

}
