export class BaseReview {
  constructor(
    public userId: number,
    public sku: number,
    public rating: number,
    public comment: string
  ) { }
}

export class Review extends BaseReview {
  constructor(
    public reviewId: number,
    userId: number,
    sku: number,
    rating: number,
    comment: string
  ) {
    super(userId, sku, rating, comment);
  }
}