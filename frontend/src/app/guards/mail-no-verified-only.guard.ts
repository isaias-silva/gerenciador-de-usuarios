import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot } from '@angular/router';
import { UserService } from '../services/user/user.service';
import { Observable, map } from 'rxjs';

export const mailNoVerifiedOnlyGuard = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean => {
  const router = inject(Router)


  let valid = atob(route.params["message"]).split('+')[1]
  if (valid && valid == 'valid') {
    return true

  } else {
    alert('no')
    router.navigate(['/'])
    return false
  }

};
