package utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class TestDataLoaderTest {

    @Test
    public void shouldLoadFrameworkTestDataFromResources() {
        assertNotNull(TestDataLoader.get("admin.base.url"));
        assertFalse(TestDataLoader.get("admin.username").isBlank());
        assertFalse(TestDataLoader.get("admin.password").isBlank());
        assertFalse(TestDataLoader.get("product.category").isBlank());
    }
}
