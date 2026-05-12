package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class ProductPage extends BasePage {
    // Navigation Locators
    private By productNavLink = By.xpath(
            "//a[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'Product') or contains(@href, 'product')]"
    );
    private By addProductButton = By.xpath(
            "//*[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'add product') or contains(text(),'Add Product')]"
    );

    // Category/Subcategory Locators
    private By categorySelectByName = By.name("category");
    private By categorySelectFallback = By.xpath("//select[contains(@id,'category') or contains(@name,'category')]");
    private By subcategorySelectByName = By.name("subCategoryId");
    private By subcategorySelectFallback = By.xpath("//select[contains(@id,'subcategory') or contains(@name,'subCategoryId')]");

    // Product Details Locators
    private By productTitleInput = By.xpath("//input[@placeholder='Enter product title']");
    private By basePriceInput = By.name("basePrice");
    private By discountedPriceInput = By.name("discount_amnt");
    private By productCodeInput = By.xpath("//label[text()='Product Code']/../input");
    private By descriptionInput = By.name("description");

    // Optional Fields Locators
    private By colorInput = By.xpath("//input[contains(@placeholder,'e.g. Maroon, Navy Blue')]");
    private By quantityInput = By.xpath("//label[contains(text(),'Optional')]/../input[contains(@placeholder,'0')]");
    private By sizeInput = By.xpath("//td[text()='M']/../td/input");

    // Upload and Save Locators
    private By fileInput = By.xpath(
            "//input[@type='file' and (contains(@accept,'image') or contains(@name,'image') or contains(@id,'image'))]"
    );
    private By fileInputFallback = By.xpath("//input[@type='file']");
    private By saveButton = By.xpath(
            "//button[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'save') or contains(text(),'Save')]"
    );
    private By saveConfirmButton = By.xpath(
            "//button[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'yes') or contains(text(),'Yes')]"
    );
    private By okButton = By.xpath("//button[contains(text(),'OK')]");

    // View/Edit and Search Locators
    private By viewEditProductsLink = By.xpath("//*[contains(text(), 'View / Edit Products')]");
    private By searchInput = By.xpath("//input[contains(@placeholder,'Search products by name...')]");
    private By actionsButton = By.xpath("//*[text()='Actions']/following::button[2]");
    private By deleteConfirmButton = By.xpath(
            "//button[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'yes') or contains(text(),'Yes')]"
    );

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    // Navigation Methods
    public void navigateToProducts() throws InterruptedException {
        System.out.println("Step: Navigating to Products page");
        click(productNavLink);
        sleep(1000);
    }

    public void clickAddProduct() throws InterruptedException {
        System.out.println("Step: Clicking Add Product");
        click(addProductButton);
        sleep(1000);
    }

    // Category/Subcategory Methods
    public void selectCategory(String category) throws InterruptedException {
        System.out.println("Step: Selecting category: " + category);
        WebElement categorySelect = findOptionalElement(categorySelectByName);
        if (categorySelect == null) {
            categorySelect = waitForElement(categorySelectFallback);
        }
        categorySelect.sendKeys(category);
        sleep(500);
    }

    public void selectSubcategory(String subcategory) throws InterruptedException {
        System.out.println("Step: Selecting subcategory: " + subcategory);
        WebElement subcategorySelect = findOptionalElement(subcategorySelectByName);
        if (subcategorySelect == null) {
            subcategorySelect = waitForElement(subcategorySelectFallback);
        }
        subcategorySelect.sendKeys(subcategory);
        sleep(500);
    }

    // Product Details Methods
    public void enterProductTitle(String title) {
        System.out.println("Step: Entering product title: " + title);
        sendKeys(productTitleInput, title);
    }

    public void enterBasePrice(String price) {
        System.out.println("Step: Entering base price: " + price);
        sendKeys(basePriceInput, price);
    }

    public void enterDiscountedPrice(String price) {
        System.out.println("Step: Entering discounted price: " + price);
        sendKeys(discountedPriceInput, price);
    }

    public String getProductCode() {
        System.out.println("Step: Capturing product code");
        return getAttribute(productCodeInput, "value");
    }

    public void enterDescription(String description) {
        System.out.println("Step: Entering description: " + description);
        sendKeys(descriptionInput, description);
    }

    // Optional Fields Methods
    public void enterColor(String color) throws InterruptedException {
        WebElement colorField = findOptionalElement(colorInput);
        if (colorField != null) {
            System.out.println("Step: Entering color: " + color);
            colorField.sendKeys(color);
        }
    }

    public void enterQuantity(String quantity) throws InterruptedException {
        WebElement quantityField = findOptionalElement(quantityInput);
        if (quantityField != null) {
            System.out.println("Step: Entering quantity: " + quantity);
            quantityField.sendKeys(quantity);
        }
    }

    public void enterSize(String size) throws InterruptedException {
        WebElement sizeField = findOptionalElement(sizeInput);
        if (sizeField != null) {
            System.out.println("Step: Entering size: " + size);
            sizeField.sendKeys(size);
        }
    }

    // Upload and Save Methods
    public void uploadImage(String imagePath) throws InterruptedException {
        System.out.println("Step: Uploading image from " + imagePath);
        WebElement fileField = findOptionalElement(fileInput);
        if (fileField == null) {
            fileField = findOptionalElement(fileInputFallback);
        }
        if (fileField != null) {
            fileField.sendKeys(imagePath);
        }
    }

    public void clickSaveProduct() throws InterruptedException {
        System.out.println("Step: Clicking save product button");
        click(saveButton);
        sleep(1000);
    }

    public void confirmSave() throws InterruptedException {
        System.out.println("Step: Confirming product save");
        WebElement confirmButton = findOptionalElement(saveConfirmButton);
        if (confirmButton != null) {
            confirmButton.click();
            sleep(500);
        }

        // Handle OK popup
        WebElement okBtn = findOptionalElement(okButton);
        if (okBtn != null) {
            System.out.println("Step: Clicking OK on success popup");
            okBtn.click();
            sleep(1000);
        }
    }

    // View/Edit and Search Methods
    public void navigateToViewEditProducts() throws InterruptedException {
        System.out.println("Step: Navigating to View/Edit Products");
        click(productNavLink);
        sleep(1000);
        click(viewEditProductsLink);
        sleep(1000);
    }

    public void searchProduct(String productName) throws InterruptedException {
        System.out.println("Step: Searching for product: " + productName);
        WebElement searchField = findOptionalElement(searchInput);
        if (searchField != null) {
            searchField.clear();
            searchField.sendKeys(productName);
            searchField.sendKeys("\n");
            sleep(2000);
        }
    }

    public boolean validateProductExists(String productName) throws InterruptedException {
        System.out.println("Step: Validating product '" + productName + "' exists");
        List<WebElement> results = findElements(By.xpath("//*[contains(text(), '" + productName + "')]"));
        return results.size() > 0;
    }

    // Delete Methods
    public void deleteProduct() throws InterruptedException {
        System.out.println("Step: Clicking delete button");
        click(actionsButton);
        sleep(1000);
    }

    public void confirmDelete() throws InterruptedException {
        System.out.println("Step: Confirming product delete");
        click(deleteConfirmButton);
        sleep(1000);
    }
}
