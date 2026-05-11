# POM-Based Selenium Framework

This directory contains a fully structured Page Object Model (POM) based Selenium automation framework in both **JavaScript/TypeScript** and **Java**.

## Directory Structure

```
frameworks/
├── javascript/
│   ├── src/
│   │   ├── pageObjects/
│   │   │   ├── BasePage.ts          # Base class with common methods
│   │   │   ├── LoginPage.ts         # Login page locators and methods
│   │   │   └── ProductPage.ts       # Product page locators and methods
│   │   ├── tests/
│   │   │   └── addProductTest.ts    # Test case: Add product flow
│   │   └── utils/
│   │       └── DriverManager.ts     # WebDriver initialization
│   ├── package.json
│   └── tsconfig.json
│
└── java/
    ├── src/
    │   ├── main/
    │   │   └── java/
    │   │       ├── pageObjects/
    │   │       │   ├── BasePage.java         # Base class with common methods
    │   │       │   ├── LoginPage.java        # Login page locators and methods
    │   │       │   └── ProductPage.java      # Product page locators and methods
    │   │       └── utils/
    │   │           └── DriverManager.java    # WebDriver initialization
    │   └── test/
    │       └── java/
    │           └── tests/
    │               └── AddProductTest.java   # Test case: Add product flow
    └── pom.xml
```

## JavaScript/TypeScript Framework

### Setup
```bash
cd frameworks/javascript
npm install
```

### Run Tests
```bash
# Run add product test
npm run test:add-product

# Build TypeScript
npm run build
```

### Adding New Test Cases
1. Create a new file in `src/tests/` (e.g., `editProductTest.ts`)
2. Import the page objects:
   ```typescript
   import { LoginPage } from "../pageObjects/LoginPage";
   import { ProductPage } from "../pageObjects/ProductPage";
   import { DriverManager } from "../utils/DriverManager";
   ```
3. Use the existing locators from the page objects
4. Example:
   ```typescript
   async function testEditProduct() {
     const driver = await DriverManager.initializeDriver();
     const productPage = new ProductPage(driver);
     // ... reuse productPage locators and methods
   }
   ```

## Java Framework

### Setup
```bash
cd frameworks/java
mvn clean install
```

### Run Tests
```bash
# Run all tests
mvn test

# Run specific test
mvn test -Dtest=AddProductTest
```

### Adding New Test Cases
1. Create a new test file in `src/test/java/tests/` (e.g., `EditProductTest.java`)
2. Import the page objects:
   ```java
   import pageObjects.LoginPage;
   import pageObjects.ProductPage;
   import utils.DriverManager;
   ```
3. Use the existing locators from the page objects
4. Example:
   ```java
   @Test
   public void testEditProduct() throws InterruptedException {
       // ... reuse productPage locators and methods
   }
   ```

## Page Object Locators Reference

### LoginPage Locators
- `usernameInput` - Username/login field
- `passwordInput` - Password field
- `loginButton` - Login submit button

### ProductPage Locators
- **Navigation**: `productNavLink`, `addProductButton`, `viewEditProductsLink`
- **Category**: `categorySelect`, `subcategorySelect`
- **Product Info**: `productTitleInput`, `basePriceInput`, `discountedPriceInput`, `productCodeInput`
- **Details**: `descriptionInput`, `colorInput`, `quantityInput`, `sizeInput`
- **Upload**: `fileInput`
- **Save**: `saveButton`, `saveConfirmButton`, `okButton`
- **Search**: `searchInput`
- **Delete**: `actionsButton`, `deleteConfirmButton`

## Reusing Locators Across Test Cases

All locators are centralized in the page objects. When creating a new test case:

1. **Locate the relevant page object** (LoginPage, ProductPage, etc.)
2. **Call the existing methods** instead of writing new selectors
3. **Example of reusing locators**:
   ```typescript
   // Instead of finding elements directly
   // ❌ WRONG
   await driver.findElement(By.xpath("//input[@placeholder='Enter product title']")).sendKeys("New Title");
   
   // ✅ RIGHT - Use existing method
   await productPage.enterProductTitle("New Title");
   ```

## Test Configuration

Edit the configuration constants at the top of each test file to modify:
- `BASE_URL`
- `USERNAME` / `PASSWORD`
- `PRODUCT_NAME`, `BASE_PRICE`, `DISCOUNT_PRICE`, etc.

## Benefits of POM Architecture

✓ **Centralized Locators** - All selectors in one place, easy to update
✓ **Reusable Methods** - Write once, use in multiple tests
✓ **Maintainability** - Changes to UI only require updates in page objects
✓ **Scalability** - Add new tests without duplicating code
✓ **Readability** - Test cases are clean and easy to understand
