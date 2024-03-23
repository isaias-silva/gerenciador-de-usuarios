import { Routes } from '@angular/router';
import { LoginAndRegisterComponent } from './pages/login-and-register/login-and-register.component';
import { isLoginGuard } from './guards/is-login.guard';

import { isNotLoginGuard } from './guards/is-not-login.guard';
import { HomeTemplateComponent } from './components/templates/home.template/home.template.component';
import { HomeComponent } from './pages/home/home.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { VerifyComponent } from './pages/verify/verify.component';
import { mailVerifiedGuard } from './guards/mail-verified.guard';

export const routes: Routes = [
    {
        path: 'login', component: LoginAndRegisterComponent, canActivate: [isNotLoginGuard]
    },
    {
        path: 'register', component: LoginAndRegisterComponent, canActivate: [isNotLoginGuard]
    },
    {
        path: '', canActivate: [isLoginGuard],
        component: HomeTemplateComponent,
        children: [
            { path: '', component: HomeComponent },
            { path: 'profile', component: ProfileComponent,canActivate:[mailVerifiedGuard] },
            { path: 'validate', component: VerifyComponent },
        ]
    }
];
