# Issue to Analyzer Finding Mapping

| Hidden issue | Expected analyzer finding |
|---|---|
| Embedded assertions in page-layer helper methods | Assertions inside page layer |
| Shared static WebDriver | Race conditions / thread safety risks |
| Mixed implicit + explicit + sleep waits | Mixed wait strategy |
| Long timeouts in wait helpers | Timeout masking |
| Retry loop swallowing exceptions | Flaky masking |
| Dynamic XPath built from product name | Fragile locators / injection risk |
| Absolute XPath usage | Fragile locators |
| JavaScript click | Framework misuse |
| Stale element retry in helper | Symptom handling |
| Sensitive logging of credentials | Security risk |
| Swallowed exceptions in navigation | Hidden failures |
| AssertTrue(true) in test | Meaningless validation |
| Hardcoded QA URL, admin, Password123 | Environment rigidity |
| ChromeDriver-specific configuration | Missing cross-browser strategy |
| Shared driver and cookie deletion between tests | State leakage |
| Order flow depends on previous product creation | State leakage |
| Current time in product title | Non-repeatable tests |
| Random values in artifact creation | Non-repeatable tests |
| Hardcoded file path | Environment rigidity |
| Direct URL fallback to admin/orders | Hidden failures / environment rigidity |
| Broad catch blocks | Poor error handling |
| Dynamic string concatenation in XPath | Injection risk |
| Generic wait for any element | Timeout masking |
| Silent fallback when upload field is missing | Hidden failures |
| Print popup detection using browser handles | Symptom handling |
| Logging token and password to stdout | Security risk |
| Global shared state in DriverManager | Race conditions |
