import { Component, Input, Output, OnInit, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
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
    this.reviewService.deleteReview(this.userId, this.sku).subscribe({
      next: (response) => {
        if (response) {
          this.reviewRemoved.emit();
          console.log('Review removed');
        } else {
          console.log('Failed to remove review');
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

  canRemove(): boolean {
    const currentUserId: number | null = this.authService.getUserId();
    if (currentUserId == null) {
      return false;
    }
    let result = false;
    this.authService.getIsAdmin().subscribe((isAdmin) => {
      if (isAdmin) {
        result = true;
      }
    });

    result = this.userId === this.authService.getUserId();
    return result;
  }
}
