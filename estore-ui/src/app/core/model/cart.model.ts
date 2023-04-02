interface ProductMap {
  forEach: any;
  [sku: number]: number;
}
export class Cart {
  constructor(
    public userId: number,
    public productsMap: ProductMap,
    public count: number,
    public totalPrice: number
  ) { }
}