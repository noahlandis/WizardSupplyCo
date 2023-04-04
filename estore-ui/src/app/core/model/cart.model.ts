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

export class CartEntry {
  constructor(public sku: number,
              public name: string,
              public price: number,
              public quantity: number) {}
}

export class CartDetails {
  constructor(public userId: number,
              public cartEntries: CartEntry[],
              public cartCount: number,
              public subtotal: number,
              public tax: number,
              public shipping: number,
              public totalPrice: number) {}
}