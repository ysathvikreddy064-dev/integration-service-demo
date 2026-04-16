🚀 Integration Service Demo — Production‑Grade External API Proxy

By Sathvik



This service demonstrates how I design integration-heavy backend systems — the exact type of systems MTI builds and maintains.

It focuses on resilience, observability, clean architecture, and debuggability, which are critical for real-world service-to-service communication.



🧩 1. What This Service Does

This microservice exposes:



Code

GET /api/external-users/{id}

It acts as a proxy to an external system (JSONPlaceholder):



Code

https://jsonplaceholder.typicode.com/users/{id}

The service adds:



Retry logic for transient failures



Structured error handling



Correlation ID logging



Consistent API responses



Clean separation of concerns



This is a realistic example of how I build integration services in production.



🧱 2. Architecture Overview

Code

Client

&#x20;  ↓

\[ExternalUserController] → Validates input, logs request

&#x20;  ↓

\[ExternalUserService] → Calls external API using WebClient

&#x20;  ↓

\[Retry Layer] → Retries transient failures (timeouts, 5xx)

&#x20;  ↓

\[Exception Handler] → Maps errors to clean API responses

&#x20;  ↓

\[Correlation Filter] → Adds correlationId to every log line

Why this architecture matters

Controller stays thin → only HTTP concerns



Service handles integration logic → easier to test \& maintain



Retry layer → protects against flaky external systems



Exception handler → consistent error responses



Correlation ID → trace a request across multiple services



This is the exact pattern used in modern microservice ecosystems.



🌐 3. External API Integration (WebClient)

The service uses Spring WebClient, a non-blocking HTTP client ideal for integration-heavy workloads.



Example call flow:

Code

Client → integration-service → JSONPlaceholder → integration-service → Client

Why WebClient?

Reactive \& efficient



Better for high-throughput integrations



Built-in support for reactive error handling



🔁 4. Retry Logic (Spring Retry)

The service retries 3 times when:



The external API returns 5xx



The external API times out



Network issues occur



Retry annotation:

java

@Retryable(

&#x20;   retryFor = { ExternalServiceUnavailableException.class },

&#x20;   maxAttempts = 3,

&#x20;   backoff = @Backoff(delay = 500)

)

Why retries matter

External systems fail — that’s normal.

Retries smooth out transient issues and prevent unnecessary outages.



⚠️ 5. Error Handling

Centralized in GlobalExceptionHandler.



External API returns 4xx

Mapped to:



Code

502 Bad Gateway

{

&#x20; "success": false,

&#x20; "message": "User not found in external system with id: X"

}

External API returns 5xx

Logged



Retried



If still failing → return:



Code

502 Bad Gateway

{

&#x20; "success": false,

&#x20; "message": "External service unavailable"

}

Unexpected errors

Mapped to:



Code

500 Internal Server Error

Why this matters

Predictable API behavior



Cleaner controllers



Easier debugging



📊 6. Logging \& Observability

Every request includes a correlationId:



Extracted from X-Correlation-Id header



Or auto-generated



Added to MDC



Included in every log line



Example log:

Code

{"ts":"2026-04-16T15:57:03.159Z","level":"INFO","correlationId":"c12f...","logger":"ExternalUserController","msg":"GET /api/external-users/1"}

Why this matters

This is how real distributed systems are debugged — especially in integration-heavy environments like MTI.



📦 7. Consistent API Response Wrapper

All responses follow:



json

{

&#x20; "success": true/false,

&#x20; "message": "Readable message",

&#x20; "data": { ... }

}

This makes frontend and downstream services easier to integrate.



🧪 8. How to Run

bash

mvn clean spring-boot:run

Service runs at:



Code

http://localhost:8082



🧠 9. Troubleshooting Guide

❗ 502 Bad Gateway

Cause: External API returned 4xx or 5xx

Fix:



Check external API availability



Check logs filtered by correlationId



❗ 500 Internal Server Error

Cause: Unexpected exception

Fix:



Inspect logs



Verify request format



❗ Logs missing correlationId

Cause: Missing header

Fix:  

Add:



Code

X-Correlation-Id: <uuid>

10. Summary

This project is intentionally small but built with real production engineering principles.

It shows how I (Sathvik) think about:



Integrations



Resilience



Logging



Observability



Maintainability



Documentation

