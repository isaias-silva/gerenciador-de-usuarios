import { Injectable } from '@angular/core';
import { environment } from '../../enviroments/enviroment';
import { HttpClient } from '@angular/common/http';
import { IResponseAuth } from '../../interfaces/api/response.auth.interface';
import { tap } from 'rxjs';
import { IbodyLogin } from '../../interfaces/api/body.login.interface';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = environment.api;

  constructor(private http: HttpClient) { }

  login(body: IbodyLogin) {

    return this.http.post<IResponseAuth>(`${this.apiUrl}/user/login`, body).pipe(tap((response) => {

      localStorage.setItem('auth-token', response.token)

    }))

  }
  logout(){
    localStorage.removeItem('auth-token')

  }
}
