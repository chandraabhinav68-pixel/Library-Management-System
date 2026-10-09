# Book Haven — Review 1 presentation outline

> Replace team names and attach your own screenshots before exporting this outline as slides or PDF.

## Slide 1 — Title

**Book Haven: Library Management System**  
Java 21 · Jakarta Servlets · JDBC · H2  
Team: _add names_

## Slide 2 — Problem and users

- Small library desks need a fast way to check availability and track loans.
- Primary user: librarian / library staff.
- Core workflows: catalog search, member registration, issuing, and returns.

## Slide 3 — Proposed solution

- Responsive browser dashboard with catalog and circulation summaries.
- Servlet API connects the interface to typed Java service workflows.
- JDBC stores books, members, and borrowing history in H2.

## Slide 4 — System architecture

Show this flow as a diagram: **Browser → Jakarta Servlets → LibraryService → JDBC repositories → H2 database**.

## Slide 5 — Database design

Show the ERD from `DATABASE_DESIGN.md`: Book 1-to-many Loan and Member 1-to-many Loan. Explain that an active loan is a loan with no return date and availability is derived from active loans.

## Slide 6 — Interface

Insert desktop and mobile screenshots from `docs/images/`. Point out search, available-copy count, and the issue/return actions.

## Slide 7 — Java concepts and JDBC

- OOP: shared entity abstraction and concrete domain types.
- Interfaces and generics: repository contracts return `List<T>`.
- Exceptions: validation and persistence failures become clear API errors.
- JDBC: prepared statements and transactions for circulation operations.
- Concurrency: scheduled overdue scan alongside HTTP request handling.

## Slide 8 — Demo and next steps

Demonstrate search → add/register → issue → return. Future work: authentication, fines, notifications, and deployment.
