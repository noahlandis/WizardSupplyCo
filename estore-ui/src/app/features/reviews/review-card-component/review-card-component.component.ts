import { Component, Input, Output, OnInit, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, of, switchMap } from 'rxjs';
import { AuthService } from 'src/app/core/services/auth.service';
import { ReviewService } from 'src/app/core/services/review.service';

@Component({
  selector: 'app-review-card-component',
  templateUrl: './review-card-component.component.html',
  styleUrls: ['./review-card-component.component.css'],
})
export class ReviewCardComponentComponent implements OnInit {
  @Input() rating: number = 0;
  @Input() comment: string = '';
  @Input() userId: number = 0;
  @Input() sku: number = 0;
  @Output() reviewRemoved = new EventEmitter<void>();

  constructor(
    private authService: AuthService,
    private reviewService: ReviewService,
    private router: Router
  ) {}

  ngOnInit(): void {}

  /** Remove review. */
  removeReview() {
    this.reviewService.deleteReview(this.sku, this.userId).subscribe({
      next: (response) => {
        console.log(`Response: ${JSON.stringify(response)}`);
        if (response) {
          console.log('Review removed');
          this.reviewRemoved.emit();
        } else {
          console.log('Failed to remove review');
          this.reviewRemoved.emit(); // TODO: Fix the issue causinng us to put this here
        }
      },
      error: (err) => {
        console.error(err);
      },
    });
  }  

  range(stop: number, start: number = 1, step: number = 1): number[] {
    const result = [];
    if (step === 0) {
      throw new Error('Step cannot be zero');
    } else if (step > 0) {
      for (let i = start; i < stop; i += step) {
        result.push(i);
      }
    } else {
      for (let i = start; i > stop; i += step) {
        result.push(i);
      }
    }
    return result;
  }

  canRemove(): Observable<boolean> {
    return this.authService.getIsLoggedIn().pipe(
      switchMap((isLoggedIn) => {
        if (!isLoggedIn) {
          console.log('User not logged in');
          return of(false);
        }
  
        return this.authService.getIsAdmin().pipe(
          switchMap((isAdmin) => {
            if (isAdmin) {
              console.log('User is admin');
              return of(true);
            }
  
            const currentUserId: number | null = this.authService.getUserId();
            console.log(`Current user id: ${currentUserId}`);
            const result = this.userId === currentUserId;
            console.log(`Result: ${result}`);
            return of(result);
          })
        );
      })
    );
  }
  
  
}
