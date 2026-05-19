import { DriverManager } from "./src/utils/DriverManager";

(async () => {
  try {
    console.log("Starting diagnostic Chrome startup test...");
    const driver = await DriverManager.initializeDriver();
    console.log("Driver created successfully");
    await driver.quit();
    console.log("Driver quit successfully");
  } catch (error) {
    console.error("Diagnostic error:", error);
    process.exit(1);
  }
})();