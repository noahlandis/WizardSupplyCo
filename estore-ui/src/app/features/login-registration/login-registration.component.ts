import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { AuthService } from 'src/app/core/services/auth.service';

@Component({
  selector: 'app-login-registration',
  templateUrl: './login-registration.component.html',
  styleUrls: ['./login-registration.component.scss']
})
export class LoginRegistrationComponent implements OnInit {
  loginForm: FormGroup;
  registerForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
    ) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['']
    });

    this.registerForm = this.fb.group({
      username: ['', Validators.required],
      password: ['']
    });
  }

  ngOnInit(): void {}

  /** Login form submission handler */
  onSubmitLoginForm(): void {
    if (!this.loginForm.valid)
      return;

    const { username } = this.loginForm.value;
    console.log('Login form submitted with username:', username);

    // call login on the auth service
    this.authService.login(username).subscribe({
      next: (loginSuccess) => {
        if (loginSuccess) {
          console.log('Login successful');
          if (this.authService.getIsAdmin().getValue())
            this.router.navigate(['/admin']);
          else this.router.navigate(['/']);
        } else {
          console.log('Login failed: invalid username');
          this.loginForm.controls['username'].setErrors({ invalidUsername: true });
        }
      },
      error: (e) => {
        console.log('Login failed with error:', e);
      }
    });
  }

  /** Registration form submission handler */
  onSubmitRegisterForm(): void {
    if (!this.registerForm.valid)
      return;

    const { username } = this.registerForm.value;
    console.log('Register form submitted with username:', username);

    // call register on the auth service
    this.authService.register(username).subscribe({
      next: (registerSuccess) => {
        if (registerSuccess) {
          console.log('Register successful');
          this.router.navigate(['/']);
        } else {
          console.log('Register failed: username taken');
          this.registerForm.controls['username'].setErrors({ usernameTaken: true });
        }
      },
      error: (e) => {
        console.log('Register failed with error:', e);
      }
    });
  }
}
