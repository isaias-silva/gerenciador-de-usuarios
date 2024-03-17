import { FormGroup } from "@angular/forms";

export interface IformUser{

    form:FormGroup
    errors:string[]
    errorClasses:string[]
    
    submitForm:()=>void
    drawErrors:()=>void
    clearErrors:()=>void

    
}