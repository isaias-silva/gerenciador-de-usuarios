import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-verify',
  standalone: true,
  imports: [ReactiveFormsModule,CommonModule],
  templateUrl: './verify.component.html',

})
export class VerifyComponent {
  form = new FormGroup({
    code: new FormControl<string | null>(null, [Validators.required])
  })
  error?:string;

  submit() {
   this.error=undefined
    if (this.form.invalid) {
      this.error="digite o código que recebeu por e-mail"
      return
    }
  }
}
