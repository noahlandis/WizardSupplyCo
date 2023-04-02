import { Component, OnInit } from '@angular/core';
import { AuthService } from './core/services/auth.service';
import { CartsService } from './core/services/carts.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'estore-ui';

  constructor(private authService: AuthService, private cartsService: CartsService) {}

  ngOnInit() {
    this.authService.getIsLoggedIn().subscribe((isLoggedIn) => {
      // If the user is logged in, update the number of products in the cart
      // This will refresh the badge on the cart icon
      if (isLoggedIn) {
        const userId = this.authService.getUserId();
        if (userId)
          this.cartsService.updateNumberOfProductsInCart(userId);
      }
    });
  }
}
