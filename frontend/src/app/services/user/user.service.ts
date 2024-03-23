import { Injectable } from '@angular/core';
import { environment } from '../../enviroments/enviroment';
import { HttpClient } from '@angular/common/http';
import { IResponseAuth } from '../../interfaces/api/response.auth.interface';
import { Iuser } from '../../interfaces/api/user.data.interface';
import { BehaviorSubject, Observable, catchError, map, of } from 'rxjs';
import { IupdateUser } from '../../interfaces/api/user.update.interface';


@Injectable({
    providedIn: 'root'
})
export class UserService {


    private apiUrl = environment.api;

    constructor(private http: HttpClient) { }

    userSubject: BehaviorSubject<Iuser | null> = new BehaviorSubject<Iuser | null>(null);

    me() {

        return this.http.get<Iuser>(`${this.apiUrl}/user/me`, { headers: { "authorization": `Bearer ${this.getToken()}` } })
            .pipe(catchError((err, caught) => {
                localStorage.removeItem('auth-token')
                throw err
            })).pipe(map((v) => {
                this.userSubject.next(v)
                return v
            }))
    }

    update(doc: IupdateUser) {
      
        return this.http.put<IResponseAuth>(`${this.apiUrl}/user/update`, doc, { headers: { "authorization": `Bearer ${this.getToken()}` } })


    }
    updateProfile(file: File) {
        const formData = new FormData();

        formData.append("file", file);

        return this.http.put<IResponseAuth>(`${this.apiUrl}/user/update/profile`, formData, { headers: { "authorization": `Bearer ${this.getToken()}` } })
    }

    
    getToken() {
        return localStorage.getItem('auth-token')

    }

}