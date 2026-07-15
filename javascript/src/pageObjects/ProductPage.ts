import { By, WebDriver } from "selenium-webdriver";
import { BasePage } from "./BasePage";

export class ProductPage extends BasePage {
  // Navigation Locators
  private productNavLink = By.xpath(
    "//a[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'product') or contains(@href, 'product')]"
  );
  private addProductButton = By.xpath(
    "//*[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'add product') or contains(text(),'Add Product')]"
  );

  // Category/Subcategory Locators
  private categorySelectByName = By.name("category");
  private categorySelectFallback = By.xpath("//select[contains(@id,'category') or contains(@name,'category')]");
  private subcategorySelectByName = By.name("subCategoryId");
  private subcategorySelectFallback = By.xpath("//select[contains(@id,'subcategory') or contains(@name,'subCategoryId')]");

  // Product Details Locators
  private productTitleInput = By.xpath("//input[@placeholder='Enter product title']");
  private basePriceInput = By.name("basePrice");
  private discountedPriceInput = By.name("discount_amnt");
  private productCodeInput = By.xpath("//label[text()='Product Code']/../input");
  private descriptionInput = By.name("description");

  // Optional Fields Locators
  private colorInput = By.xpath("//input[contains(@placeholder,'e.g. Maroon, Navy Blue')]");
  private quantityInput = By.xpath("//label[contains(text(),'Optional')]/../input[contains(@placeholder,'0')]");
  private sizeInput = By.xpath("//td[text()='M']/../td/input");

  // Upload and Save Locators
  private fileInput = By.xpath(
    "//input[@type='file' and (contains(@accept,'image') or contains(@name,'image') or contains(@id,'image'))]"
  );
  private fileInputFallback = By.xpath("//input[@type='file']");
  private saveButton = By.xpath(
    "//button[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'save') or contains(text(),'Save')]"
  );
  private saveConfirmButton = By.xpath(
    "//button[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'yes') or contains(text(),'Yes')]"
  );
  private okButton = By.xpath("//button[contains(text(),'OK')]");

  // View/Edit and Search Locators
  private viewEditProductsLink = By.xpath("//*[contains(text(), 'View / Edit Products')]");
  private searchInput = By.xpath("//input[contains(@placeholder,'Search products by name...')]");
  private actionsButton = By.xpath("//*[text()='Actions']/following::button[2]");
  private deleteConfirmButton = By.xpath(
    "//button[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'yes') or contains(text(),'Yes')]"
  );

  constructor(driver: WebDriver) {
    super(driver);
  }

  // Navigation Methods
  async navigateToProducts(): Promise<void> {
    console.log("Step: Navigating to Products page");
    await this.click(this.productNavLink);
    await this.waitForElement(this.addProductButton);
  }

  async clickAddProduct(): Promise<void> {
    console.log("Step: Clicking Add Product");
    await this.click(this.addProductButton);
    await this.waitForElement(this.productTitleInput);
  }

  // Category/Subcategory Methods
  async selectCategory(category: string): Promise<void> {
    console.log(`Step: Selecting category: ${category}`);
    const categorySelect =
      (await this.findOptionalElement(this.categorySelectByName)) ||
      (await this.waitForElement(this.categorySelectFallback));
    await categorySelect.sendKeys(category);
  }

  async selectSubcategory(subcategory: string): Promise<void> {
    console.log(`Step: Selecting subcategory: ${subcategory}`);
    const subcategorySelect =
      (await this.findOptionalElement(this.subcategorySelectByName)) ||
      (await this.waitForElement(this.subcategorySelectFallback));
    await subcategorySelect.sendKeys(subcategory);
  }

  // Product Details Methods
  async enterProductTitle(title: string): Promise<void> {
    console.log(`Step: Entering product title: ${title}`);
    await this.sendKeys(this.productTitleInput, title);
  }

  async enterBasePrice(price: string): Promise<void> {
    console.log(`Step: Entering base price: ${price}`);
    await this.sendKeys(this.basePriceInput, price);
  }

  async enterDiscountedPrice(price: string): Promise<void> {
    console.log(`Step: Entering discounted price: ${price}`);
    await this.sendKeys(this.discountedPriceInput, price);
  }

  async getProductCode(): Promise<string | null> {
    console.log("Step: Capturing product code");
    return await this.getAttribute(this.productCodeInput, "value");
  }

  async enterDescription(description: string): Promise<void> {
    console.log(`Step: Entering description: ${description}`);
    await this.sendKeys(this.descriptionInput, description);
  }

  // Optional Fields Methods
  async enterColor(color: string): Promise<void> {
    const colorField = await this.findOptionalElement(this.colorInput);
    if (colorField) {
      console.log(`Step: Entering color: ${color}`);
      await colorField.sendKeys(color);
    }
  }

  async enterQuantity(quantity: string): Promise<void> {
    const quantityField = await this.findOptionalElement(this.quantityInput);
    if (quantityField) {
      console.log(`Step: Entering quantity: ${quantity}`);
      await quantityField.sendKeys(quantity);
    }
  }

  async enterSize(size: string): Promise<void> {
    const sizeField = await this.findOptionalElement(this.sizeInput);
    if (sizeField) {
      console.log(`Step: Entering size: ${size}`);
      await sizeField.sendKeys(size);
    }
  }

  // Upload and Save Methods
  async uploadImage(imagePath: string): Promise<void> {
    console.log(`Step: Uploading image from ${imagePath}`);
    const fileField =
      (await this.findOptionalElement(this.fileInput)) || (await this.findOptionalElement(this.fileInputFallback));
    if (fileField) {
      await fileField.sendKeys(imagePath);
    }
  }

  async clickSaveProduct(): Promise<void> {
    console.log("Step: Clicking save product button");
    await this.click(this.saveButton);
  }

  async confirmSave(): Promise<void> {
    console.log("Step: Confirming product save");
    const confirmButton = await this.findOptionalElement(this.saveConfirmButton);
    if (confirmButton) {
      await confirmButton.click();
    }

    const okBtn = await this.findOptionalElement(this.okButton);
    if (okBtn) {
      console.log("Step: Clicking OK on success popup");
      await okBtn.click();
    }
  }

  // View/Edit and Search Methods
  async navigateToViewEditProducts(): Promise<void> {
    console.log("Step: Navigating to View/Edit Products");
    await this.click(this.productNavLink);
    await this.click(this.viewEditProductsLink);
    await this.waitForElement(this.searchInput);
  }

  async searchProduct(productName: string): Promise<void> {
    console.log(`Step: Searching for product: ${productName}`);
    const searchField = await this.findOptionalElement(this.searchInput);
    if (searchField) {
      await searchField.clear();
      await searchField.sendKeys(productName);
      await searchField.sendKeys("\n");
    }
  }

  async validateProductExists(productName: string): Promise<boolean> {
    console.log(`Step: Validating product '${productName}' exists`);
    const results = await this.findElements(By.xpath(`//*[contains(text(), '${productName}')]`));
    return results.length > 0;
  }

  // Delete Methods
  async deleteProduct(): Promise<void> {
    console.log("Step: Clicking delete button");
    await this.click(this.actionsButton);
  }

  async confirmDelete(): Promise<void> {
    console.log("Step: Confirming product delete");
    await this.click(this.deleteConfirmButton);
  }
}
