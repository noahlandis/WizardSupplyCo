export class Cart {
  constructor(
    public userId: number,
    public productsMap: Map<number, number>,
    public count: number,
    public total: number
  ) { }
}