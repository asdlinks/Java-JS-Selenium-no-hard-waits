# Enterprise E-commerce Selenium Framework

This repository contains a realistic enterprise-style Selenium automation framework for a QA team. The implementation includes a mix of page objects, reusable utilities, and end-to-end scenarios that appear legitimate while intentionally embedding subtle defects for analyzer training.

## Structure

- src/main/java/pageObjects - page object layer
- src/main/java/utils - common utilities and data models
- src/test/java/tests - test scenarios
- src/test/resources - TestNG configuration and test data

## Key Design Notes

- The framework is intentionally built to look production-ready while hiding multiple analyzer-detectable issues.
- Most defects are embedded in business workflows rather than placed in obvious test-only classes.
- The suite covers admin, customer portal, file handling, receipt download, and order operations.

## Suggested Execution

```bash
mvn test -Dtest=AdminDataDrivenProductFlowTest,CustomerPortalDataCaptureTest,DownloadReceiptTest
```

## Intentional Smell Coverage

The suite includes defects related to:
- embedded assertions in page layer
- brittle and dynamic locators
- mixed waits and over-waiting
- retry loops masking failures
- hardcoded environment values and credentials
- stale element handling and JS clicks
- sensitive logging and swallowed exceptions
- shared static WebDriver and state leakage
- meaningless assertions and non-deterministic checks
