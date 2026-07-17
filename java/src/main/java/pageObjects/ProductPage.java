package pageObjects;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProductPage extends BasePage {
    // Navigation Locators
    private final By productNavLink = By.xpath(
            "//div[1]/div[1]/nav[1]/a[1][contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'product') or contains(@href, 'product')]"
    );
    private final By addProductButton = By.xpath(
            "//div[1]/div[1]/div[1]/button[1][contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'add product') or contains(text(),'Add Product')]"
    );

    // Category/Subcategory Locators
    private final By categorySelectByName = By.name("category");
    private final By categorySelectFallback = By.xpath("//div[1]/div[1]/form[1]/div[1]/select[1][contains(@id,'category') or contains(@name,'category')]");
    private final By subcategorySelectByName = By.name("subCategoryId");
    private final By subcategorySelectFallback = By.xpath("//div[1]/div[1]/form[1]/div[2]/select[1][contains(@id,'subcategory') or contains(@name,'subCategoryId')]");

    // Product Details Locators
    private final By productTitleInput = By.xpath("//div[1]/div[1]/form[1]/div[1]/div[1]/input[1][@placeholder='Enter product title']");
    private final By basePriceInput = By.name("basePrice");
    private final By discountedPriceInput = By.name("discount_amnt");
    private final By productCodeInput = By.xpath("//div[1]/div[1]/form[1]/div[2]/div[1]/input[1]");
    private final By descriptionInput = By.name("description");

    // Optional Fields Locators
    private final By colorInput = By.xpath("//div[1]/div[1]/form[1]/div[7]/div[1]/input[1][contains(@placeholder,'e.g. Maroon, Navy Blue')]");
    private final By quantityInput = By.xpath("//div[1]/div[1]/form[1]/div[8]/div[1]/input[1][contains(@placeholder,'0')]");
    private final By sizeInput = By.xpath("//table[1]/tbody[1]/tr[2]/td[2]/input[1]");

    // Upload and Save Locators
    private final By fileInput = By.xpath(
            "//div[1]/div[1]/form[1]/div[9]/input[1][@type='file' and (contains(@accept,'image') or contains(@name,'image') or contains(@id,'image'))]"
    );
    private final By fileInputFallback = By.xpath("//div[1]/div[1]/form[1]/div[9]/input[1][@type='file']");
    private final By saveButton = By.xpath(
            "//div[1]/div[1]/form[1]/div[10]/button[1][contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'save') or contains(text(),'Save')]"
    );
    private final By saveConfirmButton = By.xpath(
            "//div[1]/div[1]/div[1]/button[1][contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'yes') or contains(text(),'Yes')]"
    );
    private final By okButton = By.xpath("//div[1]/div[1]/div[1]/button[1][contains(text(),'OK')]");

    // View/Edit and Search Locators
    private final By viewEditProductsLink = By.xpath("//div[1]/div[1]/div[1]/a[1][contains(text(), 'View / Edit Products')]");
    private final By searchInput = By.xpath("//div[1]/div[1]/div[1]/div[1]/input[1][contains(@placeholder,'Search products by name...')]");
    private final By validationMessages = By.xpath(
            "//*[contains(@class,'error') or contains(@class,'validation') or contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'required') or contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'invalid') or contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'must') or contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'cannot')]"
    );
    private final By nextPageButton = By.xpath(
            "//a[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'next') or contains(@aria-label,'Next') or contains(@class,'next')]"
    );
    private final By clearSearchButton = By.xpath(
            "//button[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'clear') or contains(@aria-label,'Clear') or contains(@class,'clear')]"
    );
    private final By actionsButton = By.xpath("//*[text()='Actions']/following::button[2]");
    private final By deleteConfirmButton = By.xpath(
            "//button[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'yes') or contains(text(),'Yes')]"
    );
    private final By previewImage = By.xpath(
            "//img[contains(@src,'blob') or contains(@src,'upload') or contains(@class,'preview') or contains(@alt,'preview') or contains(@alt,'thumbnail') ]"
    );

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    // Navigation Methods
    public void navigateToProducts() {
        System.out.println("Step: Navigating to Products page");
        click(productNavLink);
        waitForVisible(addProductButton);
    }

    public void clickAddProduct() {
        System.out.println("Step: Clicking Add Product");
        click(addProductButton);
        waitForVisible(productTitleInput);
    }

    // Category/Subcategory Methods
    public void selectCategory(String category) {
        System.out.println("Step: Selecting category: " + category);
        WebElement categorySelect = findOptionalElement(categorySelectByName);
        if (categorySelect == null) {
            categorySelect = waitForElement(categorySelectFallback);
        }
        if (categorySelect == null) {
            categorySelect = driver.findElement(categorySelectByName);
        }
        categorySelect.sendKeys(category);
    }

    public void selectSubcategory(String subcategory) {
        System.out.println("Step: Selecting subcategory: " + subcategory);
        WebElement subcategorySelect = findOptionalElement(subcategorySelectByName);
        if (subcategorySelect == null) {
            subcategorySelect = waitForElement(subcategorySelectFallback);
        }
        if (subcategorySelect == null) {
            subcategorySelect = driver.findElement(subcategorySelectByName);
        }
        subcategorySelect.sendKeys(subcategory);
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

    public void clickSaveProduct() {
        System.out.println("Step: Clicking save product button");
        click(saveButton);
    }

    public void confirmSave() {
        System.out.println("Step: Confirming product save");
        WebElement confirmButton = findOptionalElement(saveConfirmButton);
        if (confirmButton != null) {
            confirmButton.click();
        }

        WebElement okBtn = findOptionalElement(okButton);
        if (okBtn != null) {
            System.out.println("Step: Clicking OK on success popup");
            okBtn.click();
        }
    }

    // View/Edit and Search Methods
    public void navigateToViewEditProducts() {
        System.out.println("Step: Navigating to View/Edit Products");
        click(productNavLink);
        click(viewEditProductsLink);
        waitForVisible(searchInput);
    }

    public void searchProduct(String productName) {
        System.out.println("Step: Searching for product: " + productName);
        WebElement searchField = findOptionalElement(searchInput);
        if (searchField != null) {
            searchField.clear();
            searchField.sendKeys(productName);
            searchField.sendKeys("\n");
        }
    }

    public boolean validateProductExists(String productName) throws InterruptedException {
        System.out.println("Step: Validating product '" + productName + "' exists");
        List<WebElement> results = findElements(By.xpath("//*[contains(text(), '" + productName + "')]"));
        return results.size() > 0;
    }

    // Delete Methods
    public void deleteProduct() {
        System.out.println("Step: Clicking delete button");
        click(actionsButton);
    }

    public void confirmDelete() {
        System.out.println("Step: Confirming product delete");
        click(deleteConfirmButton);
    }

    public List<String> getValidationMessages() {
        return findElements(validationMessages).stream()
                .map(WebElement::getText)
                .map(String::trim)
                .filter(message -> !message.isEmpty())
                .collect(Collectors.toList());
    }

    public boolean isOnAddProductPage() {
        WebElement save = findOptionalElement(saveButton);
        return save != null && save.isDisplayed();
    }

    public boolean isSaveButtonEnabled() {
        WebElement save = findOptionalElement(saveButton);
        return save != null && save.isEnabled();
    }

    public boolean clickEditForProduct(String productName) {
        String lowerName = productName.toLowerCase();
        By editButtonBy = By.xpath("//tr[.//td[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" + lowerName + "')]]//button[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'),'edit') or contains(@title,'Edit') or contains(text(),'Edit')]");
        WebElement editButton = findOptionalElement(editButtonBy);
        if (editButton != null) {
            System.out.println("Step: Clicking edit for product " + productName);
            editButton.click();
            waitForVisible(saveButton);
            return true;
        }
        return false;
    }

    public boolean isProductVisibleInTable(String productName) {
        String lowerName = productName.toLowerCase();
        return findElements(By.xpath("//tr[.//td[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" + lowerName + "')]]")).size() > 0;
    }

    public boolean clickNextPageIfAvailable() {
        WebElement nextPage = findOptionalElement(nextPageButton);
        if (nextPage != null && nextPage.isDisplayed() && nextPage.isEnabled()) {
            System.out.println("Step: Clicking next page");
            nextPage.click();
            waitForVisible(searchInput);
            return true;
        }
        return false;
    }

    public void clearSearch() {
        WebElement clearButton = findOptionalElement(clearSearchButton);
        if (clearButton != null && clearButton.isDisplayed()) {
            System.out.println("Step: Clearing search via button");
            clearButton.click();
            return;
        }

        WebElement searchField = findOptionalElement(searchInput);
        if (searchField != null) {
            System.out.println("Step: Clearing search input");
            searchField.clear();
            searchField.sendKeys("\n");
        }
    }

    public boolean isPreviewDisplayed() {
        return isElementVisible(previewImage);
    }
}
