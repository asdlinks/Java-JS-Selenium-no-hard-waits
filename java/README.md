# Selenium Java Framework

This framework now uses a cleaner Java-only structure with shared test data stored in resource files instead of being embedded in test scripts and page objects.

## Structure

- src/main/java/pageObjects - page object layer
- src/main/java/utils - common utilities and data models
- src/main/resources - shared framework resources such as test-data.properties
- src/test/java/tests - Selenium test scenarios
- src/test/resources - TestNG configuration and additional test data

## Test Data Location

Shared test data is stored in:
- src/main/resources/test-data.properties
- src/test/resources/test-data.properties

The loader is implemented in:
- src/main/java/utils/TestDataLoader.java

## Suggested Execution

```bash
mvn test -Dtest=AdminDataDrivenProductFlowTest,CustomerPortalDataCaptureTest,DownloadReceiptTest
```
