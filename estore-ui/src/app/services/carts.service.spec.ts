import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CartsService } from './carts.service';
import { MessageService } from './message.service';

fdescribe('CartsService', () => {
  let service: CartsService;
  let httpTestingController: HttpTestingController;

  // Before each test, configure the test bed
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [CartsService, MessageService],
    });

    service = TestBed.inject(CartsService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  // After each test, assert that there are no more pending requests.
  afterEach(() => {
    httpTestingController.verify(); // Verify that there are no outstanding HTTP calls
  });

  // Test service creation
  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  // Test getCart()
  it('should get cart by user id', () => {
    const userId = 1;
    // Mock response from the server
    const mockCartResponse = {
      userId: 1,
      productsMap: { 
          13: 2,
          14: 2
      },
      count: 4,
      totalPrice: 10.00
    }

    // Call the service and define the callback to be an assertion on the response data
    service.getCart(userId).subscribe((cart) => {
      expect(cart).toEqual(mockCartResponse);
    });

    // Assert that the request is a GET to the correct URL
    const req = httpTestingController.expectOne(`http://localhost:8080/carts/${userId}`);
    expect(req.request.method).toEqual('GET');

    req.flush(mockCartResponse); // Return the mock response, triggering the callback
  });

  // Test addProductToCart()
  it('should add a product to a cart', () => {
    const userId = 1;
    const sku = 13;
    const quantity = 1;
    // Mock response from the server
    const mockCartResponse = {
      userId: 1,
      productsMap: { 
          13: 3,
          14: 2
      },
      count: 5,
      totalPrice: 12.50
    }

    // Call the service and define the callback to be an assertion on the response data
    service.addProductToCart(userId, sku, quantity).subscribe((cart) => {
      expect(cart).toEqual(mockCartResponse);
    });

    // Assert that the request is a PUT to the correct URL
    const req = httpTestingController.expectOne(`http://localhost:8080/carts/${userId}/products/${sku}?quantity=${quantity}`);
    expect(req.request.method).toEqual('PUT');

    req.flush(mockCartResponse); // Return the mock response, triggering the callback
  });

  // Test removeProductFromCart() with quantity
  it('should remove a product quantity from a cart', () => {
    const userId = 1;
    const sku = 13;
    const quantity = 1;
    // Mock response from the server
    const mockCartResponse = {
      userId: 1,
      productsMap: { 
          13: 1,
          14: 2
      },
      count: 3,
      totalPrice: 7.50
    }

    // Call the service and define the callback to be an assertion on the response data
    service.removeProductFromCart(userId, sku, quantity).subscribe((cart) => {
      expect(cart).toEqual(mockCartResponse);
    });

    // Assert that the request is a DELETE to the correct URL
    const req = httpTestingController.expectOne(`http://localhost:8080/carts/${userId}/products/${sku}/removeQuantity?quantity=${quantity}`);
    expect(req.request.method).toEqual('DELETE');

    req.flush(mockCartResponse); // Return the mock response, triggering the callback
  });

  // Test removeProductFromCart() without quantity
  it('should remove a product from a cart', () => {
    const userId = 1;
    const sku = 13;
    // Mock response from the server
    const mockCartResponse = {
      userId: 1,
      productsMap: { 
          14: 2
      },
      count: 2,
      totalPrice: 5.00
    }

    // Call the service and define the callback to be an assertion on the response data
    service.removeProductFromCart(userId, sku).subscribe((cart) => {
      expect(cart).toEqual(mockCartResponse);
    });

    // Assert that the request is a DELETE to the correct URL
    const req = httpTestingController.expectOne(`http://localhost:8080/carts/${userId}/products/${sku}`);
    expect(req.request.method).toEqual('DELETE');

    req.flush(mockCartResponse); // Return the mock response, triggering the callback
  });

  // Test clearCart()
  it('should clear a cart', () => {
    const userId = 1;
    // Mock response from the server
    const mockCartResponse = {
      userId: 1,
      productsMap: { },
      count: 0,
      totalPrice: 0.00
    }

    // Call the service and define the callback to be an assertion on the response data
    service.clearCart(userId).subscribe((cart) => {
      expect(cart).toEqual(mockCartResponse);
    });

    // Assert that the request is a DELETE to the correct URL
    const req = httpTestingController.expectOne(`http://localhost:8080/carts/${userId}/products`);
    expect(req.request.method).toEqual('DELETE');

    req.flush(mockCartResponse); // Return the mock response, triggering the callback
  });
});
