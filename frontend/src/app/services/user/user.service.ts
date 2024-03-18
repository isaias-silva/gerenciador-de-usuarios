import { Injectable } from '@angular/core';
import { environment } from '../../enviroments/enviroment';
import { HttpClient } from '@angular/common/http';
import { IResponseLogin } from '../../interfaces/api/response.login.interface';
import { Iuser } from '../../interfaces/api/user.data.interface';
import { catchError, of } from 'rxjs';


@Injectable({
    providedIn: 'root'
})
export class UserService {
    private apiUrl = environment.api;

    constructor(private http: HttpClient) { }

    me() {

        return this.http.get<Iuser>(`${this.apiUrl}/user/me`, { headers: { "authorization": `Bearer ${this.getToken()}` } })
        .pipe( catchError((err, caught) => {
            localStorage.removeItem('auth-token')
           throw err
          }))
    }
     getToken() {
        return localStorage.getItem('auth-token')

    }
    
}