# Project overview — Book Haven

## Problem

Small libraries need a dependable way to find books, track who has borrowed them, and understand which copies are available. Paper registers and disconnected spreadsheets make it easy to lose track of loans and overdue items.

## Proposed solution

Book Haven is a browser-based library desk. Staff can search the catalog, register a member, issue an available copy, and record its return. A dashboard summarizes the current catalog and circulation activity.

## Scope and user journey

1. Staff opens the dashboard and reviews catalog/loan counts.
2. Staff searches for a title or author and checks its available copies.
3. Staff registers a member if needed.
4. Staff issues a book to that member; the application records a due date 14 days later.
5. Staff records the return; available copy count updates immediately.

## Architecture

```text
Browser (HTML / CSS / JavaScript)
              │ JSON over HTTP
              ▼
       Jakarta Servlet layer
              │
       LibraryService workflows
              │ interfaces
              ▼
      JDBC repository classes
              │
          H2 database
```

The application uses a layered design: servlets translate HTTP requests, the service layer enforces library rules, repositories own SQL, and model objects represent domain entities. `LibraryEntity` provides shared identity and display behavior; concrete entity classes specialize that behavior.

## Design decisions

- Embedded H2 keeps first-run setup straightforward for a review/demo.
- Prepared statements keep user-entered search and form values out of SQL text.
- Borrow/return updates run in a database transaction so availability and loan state change together.
- The UI is a single responsive page for quick circulation workflows on desktop and mobile.

## Out of scope for this first review

Authentication/roles, fines and payments, email reminders, barcode scanning, and deployment to a public server. These can be added in later iterations.
