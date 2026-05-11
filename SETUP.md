# POM-Based Selenium Framework Setup Guide

## Project Structure Created

```
C:\Users\Aditya\selenium_test\
├── frameworks/                           # New POM-based framework
│   ├── javascript/                       # JavaScript/TypeScript implementation
│   │   ├── src/
│   │   │   ├── pageObjects/
│   │   │   │   ├── BasePage.ts          # Base page with common methods
│   │   │   │   ├── LoginPage.ts         # Login page locators & methods
│   │   │   │   └── ProductPage.ts       # Product page locators & methods
│   │   │   ├── tests/
│   │   │   │   ├── addProductTest.ts    # Main test: Create & Delete Product
│   │   │   │   └── testTemplate.ts      # Template for new tests
│   │   │   └── utils/
│   │   │       └── DriverManager.ts     # WebDriver initialization
│   │   ├── package.json                 # NPM dependencies
│   │   └── tsconfig.json                # TypeScript config
│   │
│   ├── java/                            # Java implementation
│   │   ├── src/
│   │   │   ├── main/java/
│   │   │   │   ├── pageObjects/
│   │   │   │   │   ├── BasePage.java    # Base page with common methods
│   │   │   │   │   ├── LoginPage.java   # Login page locators & methods
│   │   │   │   │   └── ProductPage.java # Product page locators & methods
│   │   │   │   └── utils/
│   │   │   │       └── DriverManager.java # WebDriver initialization
│   │   │   └── test/java/
│   │   │       └── tests/
│   │   │           ├── AddProductTest.java # Main test: Create & Delete Product
│   │   │           └── TestTemplate.java   # Template for new tests
│   │   └── pom.xml                      # Maven configuration
│   │
│   ├── README.md                        # Framework overview
│   └── QUICKSTART.md                    # Quick start guide
│
└── (Original test files remain unchanged)
```

## What Has Been Created

### 1. JavaScript/TypeScript Framework

**Page Objects:**
- `BasePage.ts` - Base class with common WebDriver methods (click, sendKeys, wait, etc.)
- `LoginPage.ts` - Login page with locators for username, password, and login button
- `ProductPage.ts` - Product page with comprehensive locators for:
  - Product form fields (title, price, description, etc.)
  - Category and subcategory dropdowns
  - Optional fields (color, quantity, size)
  - File upload
  - Search functionality
  - Delete functionality

**Tests:**
- `addProductTest.ts` - Complete test case that:
  1. Logs in
  2. Creates a product with all details
  3. Validates product exists
  4. Deletes the product
- `testTemplate.ts` - Template for creating new test cases

**Utils:**
- `DriverManager.ts` - Manages browser initialization and cleanup

**Configuration:**
- `package.json` - Dependencies for Selenium, ChromeDriver, TypeScript
- `tsconfig.json` - TypeScript compilation settings

### 2. Java Framework

**Page Objects:**
- `BasePage.java` - Base class with common WebDriver methods
- `LoginPage.java` - Login page with locators and login method
- `ProductPage.java` - Product page with all form and interaction methods

**Tests:**
- `AddProductTest.java` - JUnit test case for product creation and deletion
- `TestTemplate.java` - Template for creating new test cases

**Utils:**
- `DriverManager.java` - Manages browser initialization with WebDriverManager

**Configuration:**
- `pom.xml` - Maven configuration with Selenium, JUnit, WebDriverManager dependencies

### 3. Documentation

- `README.md` - Complete framework overview and structure
- `QUICKSTART.md` - Step-by-step guide for creating new tests

## Key Features

✅ **Centralized Locators** - All XPath/CSS selectors in page objects
✅ **Reusable Methods** - Write once, use in multiple tests
✅ **Easy Maintenance** - Change selectors in one place
✅ **Scalable** - Add new tests without duplicating code
✅ **Both Languages** - JavaScript and Java implementations
✅ **Step Tracking** - Detailed console logs for each action
✅ **Error Handling** - Try-catch blocks with meaningful error messages
✅ **2-Second Waits** - Added after step 19+ for visual tracking

## How to Set Up and Run

### JavaScript/TypeScript

```bash
# Navigate to framework
cd C:\Users\Aditya\selenium_test\frameworks\javascript

# Install dependencies
npm install

# Run the add product test
npm run test:add-product

# Build TypeScript (optional)
npm run build
```

### Java

```bash
# Navigate to framework
cd C:\Users\Aditya\selenium_test\frameworks\java

# Build and run tests
mvn clean test

# Run specific test
mvn test -Dtest=AddProductTest
```

## Adding a New Test Case

### JavaScript Example

1. Create `src/tests/myNewTest.ts`:
```typescript
import { DriverManager } from "../utils/DriverManager";
import { LoginPage } from "../pageObjects/LoginPage";
import { ProductPage } from "../pageObjects/ProductPage";

async function myNewTest() {
  const driver = await DriverManager.initializeDriver();
  const productPage = new ProductPage(driver);
  
  // Reuse existing methods:
  await productPage.navigateToProducts();
  await productPage.searchProduct("Test Product");
  const exists = await productPage.validateProductExists("Test Product");
  
  await DriverManager.quitDriver();
}

myNewTest().catch(e => console.error(e));
```

2. Run: `npx ts-node src/tests/myNewTest.ts`

### Java Example

1. Create `src/test/java/tests/MyNewTest.java`:
```java
@Test
public void myNewTest() throws InterruptedException {
  WebDriver driver = DriverManager.initializeDriver();
  ProductPage productPage = new ProductPage(driver);
  
  // Reuse existing methods:
  productPage.navigateToProducts();
  productPage.searchProduct("Test Product");
  boolean exists = productPage.validateProductExists("Test Product");
  
  DriverManager.quitDriver();
}
```

2. Run: `mvn test -Dtest=MyNewTest`

## Locators Available for Reuse

All these locators can be reused across test cases:

**Login:**
- Username/password fields
- Login button

**Product Management:**
- Category & subcategory dropdowns
- Product title, base price, discounted price
- Description, color, quantity, size
- File upload
- Save button & confirmation
- Search box
- Delete button

## Next Steps

1. Install Node/npm and Java/Maven (if not already done)
2. Navigate to the desired framework folder
3. Run the main test case to verify setup
4. Create new test cases using the provided templates
5. Expand the ProductPage with additional locators as needed

## Benefits of This POM Structure

- **Collaboration**: Multiple testers can write tests using shared page objects
- **Updates**: When UI changes, only update page objects (not 10+ test files)
- **Readability**: Tests are high-level and easy to understand
- **Debugging**: Logs show exactly where tests fail
- **Scalability**: Easy to add 100+ tests without code duplication

## Files Summary

**JavaScript/TypeScript (7 files)**
- 3 page objects (BasePage, LoginPage, ProductPage)
- 1 test (addProductTest)
- 1 template (testTemplate)
- 1 utility (DriverManager)
- 2 config files (package.json, tsconfig.json)

**Java (7 files)**
- 3 page objects (BasePage, LoginPage, ProductPage)
- 1 test (AddProductTest)
- 1 template (TestTemplate)
- 1 utility (DriverManager)
- 1 config file (pom.xml)

**Documentation (3 files)**
- README.md (framework overview)
- QUICKSTART.md (how to add tests)
- SETUP.md (this file)

Total: **17 files + original test**
