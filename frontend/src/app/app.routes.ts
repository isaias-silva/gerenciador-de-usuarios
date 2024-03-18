import { Routes } from '@angular/router';
import { LoginAndRegisterComponent } from './pages/login-and-register/login-and-register.component';
import { isLoginGuard } from './guards/is-login.guard';
import { HomeComponent } from './pages/home/home.component';
import { isNotLoginGuard } from './guards/is-not-login.guard';

export const routes: Routes = [
    {
        path: 'sign', component: LoginAndRegisterComponent, canActivate: [isNotLoginGuard]
    },
    {
        path: '', canActivate: [isLoginGuard],
        component: HomeComponent,
        children: [{ path: 'test', component: LoginAndRegisterComponent, }]
    }
];
