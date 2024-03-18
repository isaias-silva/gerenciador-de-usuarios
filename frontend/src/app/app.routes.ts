import { Routes } from '@angular/router';
import { LoginAndRegisterComponent } from './pages/login-and-register/login-and-register.component';
import { isLoginGuard } from './guards/is-login.guard';

import { isNotLoginGuard } from './guards/is-not-login.guard';
import { HomeTemplateComponent } from './components/templates/home.template/home.template.component';
import { HomeComponent } from './pages/home/home.component';

export const routes: Routes = [
    {
        path: 'sign', component: LoginAndRegisterComponent, canActivate: [isNotLoginGuard]
    },
    {
        path: '', canActivate: [isLoginGuard],
        component: HomeTemplateComponent,
        children: [{ path: '', component: HomeComponent, }]
    }
];
