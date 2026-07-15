# Architecture Diagram

```text
Browser / App Under Test
        |
        v
+-----------------------------+
|   Page Objects Layer        |
|  LoginPage | ProductPage    |
|  OrdersPage | CustomerPage |
+-----------------------------+
        |
        v
+-----------------------------+
|   Shared Utilities          |
|  DriverManager             |
|  Data Models / Snapshot    |
+-----------------------------+
        |
        v
+-----------------------------+
|   Test Scenarios           |
|  Admin / Customer / Orders |
+-----------------------------+
```

## Intentional Design Trade-offs

The architecture is deliberately shaped like a mature enterprise framework, but several components are tightly coupled and rely on global state, broad catch blocks, and dynamic selectors to make analyzer detection more realistic.
