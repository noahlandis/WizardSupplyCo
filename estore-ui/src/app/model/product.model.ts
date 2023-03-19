export class BaseProduct {
  constructor(
    public name: string,
    public price: number,
    public stockQuantity: number,
    public images: string[],
    public description: Description,
  ) { }
}

export class Product extends BaseProduct {
  constructor(
    public sku: number,
    name: string,
    price: number,
    stockQuantity: number,
    images: string[],
    description: Description,
  ) {
    super(name, price, stockQuantity, images, description);
  }
}

export class Description {
  constructor(
    public summary: string,
    public tags: string[],
  ) { }
}