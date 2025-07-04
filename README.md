# Fawry OOP Internship Task 🛒

## 💡 Task Description

Design an e-commerce system with the following features:

### 🛍️ Products
- Each product has a **name**, **price**, and **quantity**.
- Some products may **expire**, like **Cheese** or **Biscuits**, while others do **not expire**, like **TV** or **Mobile scratch cards**.
- Some products may **require shipping**, like **Cheese** or **TV**, while others do **not**, such as **scratch cards**.
- Every shippable product must provide its **weight**.

### 🧑‍🤝‍🧑 Customers and Cart
- Customers can **add products** to their cart with a specific quantity.
- The quantity added must **not exceed the available product quantity**.
- Customers can **checkout** the cart, which includes:
  - 🧾 Printing checkout details to the console:
    - Order **subtotal** (sum of item prices)
    - **Shipping fees**
    - **Total amount paid** (subtotal + shipping)
    - Customer’s **remaining balance**
  - ❌ Throwing errors if:
    - The **cart is empty**
    - The **customer's balance is insufficient**
    - A **product is expired** or **out of stock**

### 📦 Shipping
- If applicable, all products requiring shipping should be collected and passed to a `ShippingService`.
- The `ShippingService` accepts a **list of objects** that implement an interface containing only:
  - `String getName()`
  - `double getWeight()`

---

## ✅ Technologies Used
- Java 8+
- Object-Oriented Programming (OOP)
- Builder Pattern
- Optional<T> Handling
- Console I/O

---

## 🧪 Test Cases
Test scenarios are provided inside `Main.java`, including:

| Section | Description                        |
|---------|------------------------------------|
| ✅ Section 1 | Successful checkout              |
| ❌ Section 2 | Expired product in cart          |
| ❌ Section 3 | Quantity exceeds available stock |
| ❌ Section 4 | Insufficient customer balance    |
| ❌ Section 5 | Empty cart                       |
| ✅ Section 6 | Non-shippable product            |

Run the `Main` class to verify all cases.

---
