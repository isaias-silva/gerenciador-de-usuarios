import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { UserService } from '../services/user/user.service';

export const isLoginGuard: CanActivateFn = async (route, state) => {
  const router = inject(Router)
  const userService = inject(UserService)

  if (!userService.getToken()) {
    router.navigate(['/sign'])
    return false
  }
  return true


};
