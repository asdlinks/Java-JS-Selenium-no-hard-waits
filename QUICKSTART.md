# Quick Start Guide - POM-Based Selenium Framework

## What is Page Object Model (POM)?

Page Object Model is a design pattern that represents each page or component in your application as a class. Instead of writing locators directly in test cases, you centralize them in page objects. This makes your tests:
- **More maintainable**: Change a locator once, affects all tests
- **More readable**: Tests focus on what they do, not how they find elements
- **More reusable**: Share methods and locators across multiple tests

## Directory Overview

### JavaScript/TypeScript Structure
```
frameworks/javascript/
├── src/
│   ├── pageObjects/         # All page locators and methods
│   │   ├── BasePage.ts      # Common methods (click, sendKeys, wait)
│   │   ├── LoginPage.ts     # Login page (username, password, login button)
│   │   └── ProductPage.ts   # Product page (forms, buttons, search, delete)
│   ├── tests/               # Test cases that use page objects
│   │   ├── addProductTest.ts  # Working example test
│   │   └── testTemplate.ts    # Template for new tests
│   └── utils/
│       └── DriverManager.ts # Browser initialization and cleanup
└── package.json & tsconfig.json
```

### Java Structure
```
frameworks/java/
├── src/main/java/
│   ├── pageObjects/         # All page locators and methods
│   │   ├── BasePage.java    # Common methods
│   │   ├── LoginPage.java   # Login page
│   │   └── ProductPage.java # Product page
│   └── utils/
│       └── DriverManager.java # Browser initialization
├── src/test/java/
│   └── tests/
│       ├── AddProductTest.java # Working example test
│       └── TestTemplate.java   # Template for new tests
└── pom.xml
```

## How to Add a New Test Case

### Example: Creating a "Search Product" Test

#### JavaScript/TypeScript

**Step 1**: Create new file `frameworks/javascript/src/tests/searchProductTest.ts`

**Step 2**: Import page objects
```typescript
import { DriverManager } from "../utils/DriverManager";
import { LoginPage } from "../pageObjects/LoginPage";
import { ProductPage } from "../pageObjects/ProductPage";
```

**Step 3**: Write test using existing methods from ProductPage
```typescript
async function testSearchProduct() {
  const driver = await DriverManager.initializeDriver();
  const loginPage = new LoginPage(driver);
  const productPage = new ProductPage(driver);

  try {
    // Use existing ProductPage methods
    await driver.get(BASE_URL);
    await loginPage.login("admin", "password123");
    await productPage.navigateToViewEditProducts();  // Reuse this method
    await productPage.searchProduct("Test Asd Saree"); // Reuse this method
    
    const found = await productPage.validateProductExists("Test Asd Saree"); // Reuse this method
    console.log(found ? "Product found!" : "Product not found");
    
  } finally {
    await DriverManager.quitDriver();
  }
}
```

**Step 4**: Update `package.json` to add a new script (optional)
```json
"scripts": {
  "test:add-product": "ts-node src/tests/addProductTest.ts",
  "test:search-product": "ts-node src/tests/searchProductTest.ts"
}
```

**Step 5**: Run the test
```bash
npm run test:search-product
```

#### Java

**Step 1**: Create new file `frameworks/java/src/test/java/tests/SearchProductTest.java`

**Step 2**: Import page objects and JUnit
```java
import org.junit.Test;
import pageObjects.LoginPage;
import pageObjects.ProductPage;
import utils.DriverManager;
```

**Step 3**: Write test using existing methods from ProductPage
```java
@Test
public void testSearchProduct() throws InterruptedException {
  WebDriver driver = DriverManager.initializeDriver();
  LoginPage loginPage = new LoginPage(driver);
  ProductPage productPage = new ProductPage(driver);

  try {
    driver.get(BASE_URL);
    loginPage.login("admin", "password123");
    productPage.navigateToViewEditProducts();  // Reuse this method
    productPage.searchProduct("Test Asd Saree"); // Reuse this method
    
    boolean found = productPage.validateProductExists("Test Asd Saree"); // Reuse this method
    System.out.println(found ? "Product found!" : "Product not found");
    
  } finally {
    DriverManager.quitDriver();
  }
}
```

**Step 4**: Run the test
```bash
mvn test -Dtest=SearchProductTest
```

## Available Page Object Methods

### LoginPage Methods
- `login(username, password)` - Complete login process
- `enterUsername(username)` - Enter username
- `enterPassword(password)` - Enter password
- `clickLoginButton()` - Click login button

### ProductPage Methods

**Navigation**
- `navigateToProducts()` - Go to products page
- `clickAddProduct()` - Click add product button
- `navigateToViewEditProducts()` - Go to view/edit products

**Product Entry**
- `selectCategory(category)` - Select product category
- `selectSubcategory(subcategory)` - Select subcategory
- `enterProductTitle(title)` - Enter product title
- `enterBasePrice(price)` - Enter base price
- `enterDiscountedPrice(price)` - Enter discounted price
- `getProductCode()` - Get product code
- `enterDescription(description)` - Enter description
- `enterColor(color)` - Enter color
- `enterQuantity(quantity)` - Enter quantity
- `enterSize(size)` - Enter size
- `uploadImage(path)` - Upload product image
- `clickSaveProduct()` - Click save button
- `confirmSave()` - Confirm save action

**Search & Validate**
- `searchProduct(name)` - Search for product
- `validateProductExists(name)` - Check if product exists

**Delete**
- `deleteProduct()` - Click delete button
- `confirmDelete()` - Confirm delete

## Locator Examples (For Adding to Page Objects)

If you need to add new locators to ProductPage:

```typescript
// XPath examples used in ProductPage
private colorInput = By.xpath("//input[contains(@placeholder,'e.g. Maroon, Navy Blue')]");
private sizeInput = By.xpath("//td[text()='M']/../td/input");
private searchInput = By.xpath("//input[contains(@placeholder,'Search products by name...')]");
private actionsButton = By.xpath("//*[text()='Actions']/following::button[2]");

// How to add a new method to ProductPage
async enterNewField(value: string): Promise<void> {
  const field = By.xpath("//input[@placeholder='Your Field Placeholder']");
  await this.sendKeys(field, value);
}
```

## Tips for Writing Maintainable Tests

1. **Use Page Object Methods**: Instead of finding elements directly, use methods from page objects
2. **Keep Locators Centralized**: Add new locators to page objects, not in test files
3. **Add Descriptive Logs**: Use `console.log()` to track test progress
4. **Reuse Methods**: Build on existing page object methods rather than duplicating code
5. **Handle Dynamic Elements**: Use fallback locators for elements that might change position

## Common Issues & Solutions

**Issue**: Element not found
- **Solution**: Check if the locator in the page object is still correct for the current UI

**Issue**: Timeout errors
- **Solution**: Increase wait time in BasePage or verify element is visible before interaction

**Issue**: Stale element reference
- **Solution**: Use findOptionalElement() to re-find the element before each interaction

## Running All Tests

### JavaScript
```bash
npm test
```

### Java
```bash
mvn test
```
