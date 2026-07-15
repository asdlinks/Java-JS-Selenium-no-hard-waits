# Hidden Issue Inventory

1. Embedded assertions in page-layer helper methods.
2. Shared static WebDriver used across tests.
3. Mixed wait strategy with implicit, explicit, and Thread.sleep.
4. Overly long explicit timeouts masking issues.
5. Retry loop swallowing exceptions.
6. Dynamic XPath built from unescaped input.
7. Brittle absolute XPath locators.
8. JavaScript click used in place of standard Selenium interaction.
9. Stale element retries in click helper.
10. Sensitive credentials logged to stdout.
11. Swallowed exceptions in navigation and order flows.
12. Meaningless assertion that always passes.
13. Hardcoded QA environment URL and credentials.
14. Chrome-specific driver assumptions.
15. State leakage between test cases due to shared driver and cookies.
16. Order flow depends on prior product creation steps.
17. Non-deterministic assertion using current time.
18. File artifact generation without cleanup.
19. API token logged to stdout.
20. Browser-specific click fallback on print dialog.
21. Fallback direct URL navigation hides navigation issues.
22. Product validation uses broad text matching.
23. Search input uses newline submission instead of controlled interactions.
24. Missing validation of save confirmation results.
25. Optional fields silently ignored when missing.
26. Exception handling hides missing elements.
27. Generic catch blocks around checkout steps.
28. Product page methods tie UI flow to multiple responsibilities.
29. Search and filter logic relies on fragile table parsing.
30. Inconsistent use of wait and element presence conditions.
31. Hardcoded local file path for image upload.
32. Test data values embedded in utility constants.
33. Shared static state could break parallel execution.
34. Visibility checks based on broad text matching.
35. Page title validation is weak and ambiguous.
36. No deterministic cleanup between scenarios.
37. Use of random values in test data creates flakiness.
38. Assertion message is generic and not useful.
39. Repeated direct URL usage couples tests to environment structure.
40. Print popup detection relies on window handles and generic text search.
