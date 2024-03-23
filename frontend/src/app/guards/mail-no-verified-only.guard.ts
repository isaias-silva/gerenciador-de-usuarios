import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot } from '@angular/router';
import { UserService } from '../services/user/user.service';
import { Observable, map } from 'rxjs';

export const mailNoVerifiedOnlyGuard = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> => {
  const router = inject(Router)
  const userService = inject(UserService)


  return userService.me().pipe(map(user => {

    if (user.role != 'VERIFY_MAIL') {
      router.navigate(['/'])
      return false
    } else {
      return true
    }
  }))

};
