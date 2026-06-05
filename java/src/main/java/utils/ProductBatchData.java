package utils;

import java.util.ArrayList;
import java.util.List;

public final class ProductBatchData {
    private ProductBatchData() {
    }

    private static final String BASE_NAME = "Test Asd";
    private static final String BASE_PRICE = "1000";
    private static final String DISCOUNT_PRICE = "900";
    private static final String DESCRIPTION_PREFIX = "Automation batch product";
    private static final String CATEGORY = "Saree";
    private static final String SUBCATEGORY = "cotton";
    private static final String COLOR = "Red";
    private static final String SIZE = "M";
    private static final String QUANTITY = "5";
    private static final String IMAGE_PATH = "C:\\Users\\Aditya\\Downloads\\test saree img.jpg";

    public static List<ProductSpec> getBatchProducts() {
        List<ProductSpec> products = new ArrayList<>();
        for (int index = 1; index <= 10; index++) {
            String suffix = String.format("%02d", index);
            products.add(new ProductSpec(
                    BASE_NAME + suffix,
                    String.valueOf(Integer.parseInt(BASE_PRICE) + index),
                    String.valueOf(Integer.parseInt(DISCOUNT_PRICE) + index),
                    DESCRIPTION_PREFIX + " " + suffix,
                    CATEGORY,
                    SUBCATEGORY,
                    COLOR,
                    QUANTITY,
                    SIZE,
                    IMAGE_PATH
            ));
        }
        return products;
    }

    public static final class ProductSpec {
        private final String productName;
        private final String basePrice;
        private final String discountPrice;
        private final String description;
        private final String category;
        private final String subCategory;
        private final String color;
        private final String quantity;
        private final String size;
        private final String imagePath;

        public ProductSpec(String productName, String basePrice, String discountPrice,
                           String description, String category, String subCategory,
                           String color, String quantity, String size, String imagePath) {
            this.productName = productName;
            this.basePrice = basePrice;
            this.discountPrice = discountPrice;
            this.description = description;
            this.category = category;
            this.subCategory = subCategory;
            this.color = color;
            this.quantity = quantity;
            this.size = size;
            this.imagePath = imagePath;
        }

        public String getProductName() {
            return productName;
        }

        public String getBasePrice() {
            return basePrice;
        }

        public String getDiscountPrice() {
            return discountPrice;
        }

        public String getDescription() {
            return description;
        }

        public String getCategory() {
            return category;
        }

        public String getSubCategory() {
            return subCategory;
        }

        public String getColor() {
            return color;
        }

        public String getQuantity() {
            return quantity;
        }

        public String getSize() {
            return size;
        }

        public String getImagePath() {
            return imagePath;
        }
    }
}
