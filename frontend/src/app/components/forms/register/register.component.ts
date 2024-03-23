import { CommonModule } from '@angular/common';
import { Component, ElementRef, ViewChild } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ReactiveFormsModule, ValidationErrors, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { IbodyRegister } from '../../../interfaces/api/body.register.interface';
import { IformUser } from '../../../interfaces/components/form.user.interface';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { AuthService } from '../../../services/user/auth.service';
import { IerrorRegister } from '../../../interfaces/api/response.error.register';
import { ViewPasswordComponent } from '../../utils/view-password/view-password.component';
import { confirmPasswordValidator } from '../../../validators/confirmPasswordValidator';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, RouterModule, ReactiveFormsModule, MatProgressSpinnerModule, ViewPasswordComponent],
  templateUrl: './register.component.html',

})
export class RegisterComponent implements IformUser {

  constructor(private authService: AuthService, private router: Router) { }
  visiblePassword: boolean = false;
  changeVisibility(visible: boolean) {
    this.visiblePassword = visible
  };

  errors: string[] = [];
  errorClasses: string[] = ['border-red-300', 'border-2'];

  @ViewChild("name") inputName?: ElementRef

  @ViewChild("mail") inputMail?: ElementRef

  @ViewChild("password") inputPassword?: ElementRef

  @ViewChild("password_repite") inputPasswordRepite?: ElementRef

  load: boolean = false


  form = new FormGroup({
    name: new FormControl<string | null>(null, [Validators.required, Validators.min(4), Validators.max(10)]),
    mail: new FormControl<string | null>(null, [Validators.required, Validators.email]),
    password: new FormControl<string | null>(null, [Validators.required, Validators.pattern(/(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).+$/), Validators.max(12), Validators.min(7)]),
    passwordRepite: new FormControl<string | null>(null, [Validators.required],
    )

  }, { validators: confirmPasswordValidator })



  submitForm() {
    const { name, password, mail } = this.form.controls

    this.clearErrors()
    if (this.form.invalid) {

      this.drawErrors()
    } else {

      const userRegisterBody: IbodyRegister = {
        name: name.value,
        mail: mail.value,
        password: password.value
      }
      this.load = true
      this.authService.register(userRegisterBody).subscribe({
        next: (res) => {
          this.load = false
          this.router.navigate(['/validate'])
        },
        error: (err: IerrorRegister) => {
          this.load = false
          if (err.error.erros)
            this.errors.push(err.error.erros[err.error.erros.length - 1])
          else if (err.error.message)
            this.errors.push(err.error.message)

        }
      })

    }
  }
  drawErrors() {


    const { mail, password, name, passwordRepite } = this.form.controls;

    if (name.status == 'INVALID') {
      this.errors.push("nome inválido! use um nome com no minimo 4 digitos e no máximo 10.")
      this.errorClasses.forEach(errorClass => this.inputName?.nativeElement.classList.add(errorClass))

      return
    }
    if (mail.status == 'INVALID') {

      this.errors.push("e-mail inválido!")
      this.errorClasses.forEach(errorClass => this.inputMail?.nativeElement.classList.add(errorClass))

      return
    }
    if (password.status == 'INVALID') {
      this.errors.push("senha deve conter: letras números e símbolos, no mínimo 8 dígitos e no máximo 12")
      this.errorClasses.forEach(errorClass => this.inputPassword?.nativeElement.classList.add(errorClass))

      return
    }

    if (this.form.getError('PasswordNoMatch')) {
      this.errors.push("senhas devem ser iguais.")
      this.errorClasses.forEach(errorClass => this.inputPasswordRepite?.nativeElement.classList.add(errorClass))

      return
    }

  }
  clearErrors() {
    this.errors = []
    this.errorClasses.forEach(errorClass => {
      this.inputPasswordRepite?.nativeElement.classList.remove(errorClass)
      this.inputName?.nativeElement.classList.remove(errorClass)
      this.inputPassword?.nativeElement.classList.remove(errorClass)
      this.inputMail?.nativeElement.classList.remove(errorClass)
    })
  }

}
