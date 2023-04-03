export class Review {
  constructor(
    public userId: number,
    public sku: number,
    public rating: number,
    public comment: string
  ) { }
}