export class BaseProduct {
  constructor(
    public name: string,
    public price: number,
    public stockQuantity: number,
    public images: string[],
    public description: string
  ) { }
}

export class Product extends BaseProduct {
  constructor(
    public sku: number,
    name: string,
    price: number,
    stockQuantity: number,
    images: string[],
    description: string
  ) {
    super(name, price, stockQuantity, images, description);
  }
}