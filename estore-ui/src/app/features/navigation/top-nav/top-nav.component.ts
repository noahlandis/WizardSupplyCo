import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/core/services/auth.service';
import { CartsService } from 'src/app/core/services/carts.service';
import { UsersService } from 'src/app/core/services/users.service';

@Component({
  selector: 'app-top-nav',
  templateUrl: './top-nav.component.html',
  styleUrls: ['./top-nav.component.scss']
})
export class TopNavComponent implements OnInit {
  @Output() public sidenavToggle = new EventEmitter();


  constructor(
    public authService: AuthService,
    private router: Router,
    public cartsService: CartsService
  ) { }

  ngOnInit() {  }

  onLogout() {
    console.log('Logout clicked');
    this.authService.logout().subscribe((success) => {
      if (success) {
        this.router.navigate(['/']);
      } else {
        console.log('Logout failed');
      }
    });
  }

  public onToggleSidenav = () => {
    this.sidenavToggle.emit();
  }
}
