export class Product {
  constructor(
    public sku: number,
    public name: string,
    public price: number,
    public stockQuantity: number,
    public images: string[],
    public description: string
  ) { }
}