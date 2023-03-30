import { Cart } from "./cart.model";

export class BaseOrder {
  constructor(
    public firstName: string,
    public lastName: string,
    public phoneNumber: string,
    public emailAddress: string,
    public shippingAddress: ShippingAddress,
    public cart: Cart
  ) { }
}

export class Order extends BaseOrder {
  constructor(
    public orderNumber: number,
    firstName: string,
    lastName: string,
    phoneNumber: string,
    emailAddress: string,
    shippingAddress: ShippingAddress,
    cart: Cart
  ) {
    super(
      firstName,
      lastName,
      phoneNumber,
      emailAddress,
      shippingAddress,
      cart
    );
  }
}

export class ShippingAddress {
  constructor(
    public country: string,
    public state: string,
    public city: string,
    public zipCode: string,
    public addressLine1: string,
    public addressLine2: string,
    public apartmentNumber: string,
  ) { }
}