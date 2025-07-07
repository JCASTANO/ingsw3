# FastShop Order Management System

This project implements an order management microservice for FastShop e-commerce platform.

## Features

### Order Creation (POST /orders)
- Create new orders with product validation
- Stock availability checking
- Payment processing simulation (80% success rate)
- Stock reservation after successful payment
- In-memory order storage

### Order Query (GET /orders/{orderId})
- Retrieve order details by ID
- Returns 404 if order not found

## Business Rules

- Maximum 10 units per product in an order
- Only CREDIT_CARD payment method supported
- Stock validation before payment processing
- Stock reservation after successful payment
- Orders can have status: PENDING, PAID, or FAILED

## Error Handling

- **400 Bad Request**: Invalid payload (missing fields, quantity > 10, invalid payment method)
- **402 Payment Required**: Payment processing failed
- **409 Conflict**: Insufficient stock or stock reservation failure
- **404 Not Found**: Order not found
- **500 Internal Server Error**: Unexpected errors

## API Examples

### Create Order
```bash
curl -X POST http://localhost:8081/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": 123,
    "items": [
      { "productId": 456, "quantity": 2 },
      { "productId": 789, "quantity": 1 }
    ],
    "paymentMethod": "CREDIT_CARD"
  }'
```

Response:
```json
{
  "orderId": 1,
  "customerId": 123,
  "items": [
    { "productId": 456, "quantity": 2, "unitPrice": 100.00 },
    { "productId": 789, "quantity": 1, "unitPrice": 250.00 }
  ],
  "totalAmount": 450.00,
  "status": "PAID",
  "paymentMethod": "CREDIT_CARD"
}
```

### Get Order
```bash
curl -X GET http://localhost:8081/orders/1
```

## Running the Application

1. **Build the project:**
   ```bash
   ./gradlew build
   ```

2. **Run the application:**
   ```bash
   ./gradlew bootRun
   ```

3. **Run tests:**
   ```bash
   ./gradlew test
   ```

The application will start on port 8081.

## Test Data

The system includes pre-loaded inventory data:
- Product 456: 5 units at $100.00 each
- Product 789: 2 units at $250.00 each  
- Product 321: 10 units at $50.00 each

## Architecture

The implementation follows Clean Architecture principles:

- **Domain Layer**: Business models, rules, and services
- **Application Layer**: Use cases and DTOs
- **Infrastructure Layer**: Controllers, repositories, and external adapters

## Implementation Highlights

- Single Responsibility: Each class has one clear purpose
- Open/Closed: Ready for new payment methods without core changes
- Clean Code: Descriptive names, minimal duplication
- In-memory persistence: No database required as specified
- Comprehensive error handling with proper HTTP status codes
- Unit and integration tests covering core scenarios