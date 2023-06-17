import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ToolbarComponent} from './components/toolbar/toolbar.component';

const routes: Routes = [
  {
    path: 'dashboard',
    component: ToolbarComponent,
  }, {
    path: '**',
    redirectTo: '/dashboard'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
