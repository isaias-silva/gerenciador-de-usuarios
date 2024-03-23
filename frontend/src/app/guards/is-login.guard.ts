import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot } from '@angular/router';
import { UserService } from '../services/user/user.service';
import { Observable, catchError, map, of } from 'rxjs';

export const isLoginGuard = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> => {
  const router = inject(Router)
  const userService = inject(UserService)

  return userService.me().pipe(
    map(user=>{
      return true
    }),

    catchError(() => {
      
      router.navigate(['/login'])
      return of(false);
    })
  )

};
