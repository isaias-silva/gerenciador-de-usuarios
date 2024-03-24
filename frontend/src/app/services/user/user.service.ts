import { Injectable } from '@angular/core';
import { environment } from '../../enviroments/enviroment';
import { HttpClient } from '@angular/common/http';
import { IResponseAuth } from '../../interfaces/api/response.auth.interface';
import { Iuser } from '../../interfaces/api/user.data.interface';
import { BehaviorSubject, Observable, catchError, delay, map, of } from 'rxjs';
import { IupdateUser } from '../../interfaces/api/user.update.interface';
import { IResponseDefault } from '../../interfaces/api/response.default';


@Injectable({
    providedIn: 'root'
})
export class UserService {


    private apiUrl = environment.api;

    constructor(private http: HttpClient) { }


    me() {

        return this.http.get<Iuser>(`${this.apiUrl}/user/me`, { headers: { "authorization": `Bearer ${this.getToken()}` } })
            .pipe(catchError((err, caught) => {
                localStorage.removeItem('auth-token')
                throw err
            }))
    }

    update(doc: IupdateUser) {

        return this.http.put<IResponseDefault>(`${this.apiUrl}/user/update`, doc, { headers: { "authorization": `Bearer ${this.getToken()}` } })


    }
    updateProfile(file: File) {
        const formData = new FormData();

        formData.append("file", file);

        return this.http.put<IResponseDefault>(`${this.apiUrl}/user/update/profile`, formData, { headers: { "authorization": `Bearer ${this.getToken()}` } })
    }

    validate(code: string) {

        return this.http.put<IResponseDefault>(`${this.apiUrl}/user/validate`, { code }, { headers: { "authorization": `Bearer ${this.getToken()}` } }).pipe(delay(5000))

    }
    confirmChangeMail(code: string) {

        return this.http.put<IResponseDefault>(`${this.apiUrl}/user/update/confirm/mail`, { code }, { headers: { "authorization": `Bearer ${this.getToken()}` } }).pipe(delay(8000))

    }
    sendNewCode() {

        return this.http.get<IResponseDefault>(`${this.apiUrl}/user/send/newCode`, { headers: { "authorization": `Bearer ${this.getToken()}` } })

    }


    getToken() {
        return localStorage.getItem('auth-token')

    }

}