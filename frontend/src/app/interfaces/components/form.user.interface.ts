import { FormGroup } from "@angular/forms";

export interface IformUser {

    form: FormGroup
    errors: string[]
    errorClasses: string[]
    visiblePassword: boolean
    changeVisibility: (visible: boolean) => void
    submitForm: () => void
    drawErrors: () => void
    clearErrors: () => void


}