# Book Haven — Library Management System

A Java 21 web application for managing a small library. It uses embedded Tomcat, Jakarta Servlets, JDBC, and an embedded H2 database. The database file is created automatically on first launch; no separate database server is needed.

## Requirements

- JDK 21 or newer
- Apache Maven 3.9 or newer

## Run locally

```bash
mvn clean compile exec:java
```

Open [http://localhost:8080](http://localhost:8080). Stop the server with Ctrl+C. The application creates `data/bookhaven-library.mv.db` on first launch and inserts starter catalog data. The database is intentionally ignored by Git.

## Website link

Open `Open Book Haven.url` in the project folder for the direct local website link. See [docs/WEBSITE_LINK.md](docs/WEBSITE_LINK.md) for startup instructions and notes about sharing a public link with reviewers.

## Project map

```text
bookhaven-library/
├── database/                 # SQL schema and starter records
├── docs/                     # Review 1 project and database design
├── src/main/java/com/bookhaven/
│   ├── LibraryApplication.java
│   ├── config/                # JDBC connection and bootstrap
│   ├── dao/                   # JDBC data access interfaces and implementations
│   ├── exception/             # Domain and persistence exceptions
│   ├── model/                 # Book, Member, Loan, and base entity
│   ├── service/               # Library workflows and background overdue scan
│   └── web/                   # Servlets and HTML response helpers
└── src/main/webapp/           # Responsive interface assets
```

## Features

- Dashboard with live catalog, member, loan, and overdue counts.
- Search and browse books; add books to the catalog.
- Register members, issue available books, and return active loans.
- JDBC prepared statements, database constraints, transaction boundaries, and domain-specific error responses.
- Responsive library dashboard with clear empty/loading/error states.

## Review 1 rubric mapping

| Rubric area | Where to find it |
|---|---|
| Problem understanding and solution design | `docs/PROJECT_OVERVIEW.md`, `docs/DATABASE_DESIGN.md` |
| OOP, inheritance, polymorphism, interfaces, exceptions | `model/LibraryEntity.java`, `model/Book.java`, `model/Member.java`, `dao/LibraryRepository.java`, `exception/` |
| Collections and generics | Repository interfaces return `List<T>`; service layer uses typed collections |
| Multithreading and synchronization | `service/OverdueScanService.java` runs a scheduled background scan; loan changes are transactional and H2 is multi-thread safe |
| Database operation classes and JDBC | `dao/`, `config/Database.java`, `database/schema.sql` |
| Servlets and web integration | `web/` servlet endpoints, mounted by `LibraryApplication.java` |
| UI/UX and responsiveness | `src/main/webapp/`, `docs/SCREENSHOTS.md` |

## HTTP endpoints

- `GET /api/dashboard` — dashboard totals
- `GET /api/books?q=...` — search catalog
- `POST /api/books` — add book (`title`, `author`, `isbn`, `category`, `totalCopies`)
- `GET /api/members` — member list
- `POST /api/members` — register member (`name`, `email`, `phone`)
- `GET /api/loans` — recent loans
- `POST /api/loans` — issue book (`bookId`, `memberId`)
- `POST /api/loans/{id}/return` — return a loan

Request and response bodies use JSON. See `docs/API.md` for examples.

## Database

H2 runs in embedded file mode and is initialized at startup. The canonical schema and demo rows are in `database/schema.sql` and `database/seed.sql`; runtime bootstrap uses matching statements in `config/Database.java`. The initial catalog rows are examples that can be safely changed or removed.

## GitHub submission checklist

1. Create a public GitHub repository and push this folder (`git init`, `git add .`, `git commit`, then add the GitHub remote and push).
2. Do not commit `data/` or credentials. The `.gitignore` excludes generated H2 files.
3. Add your team members and screenshots to `docs/SCREENSHOTS.md` before submission.
4. Export `docs/REVIEW_1_PRESENTATION.md` to slides or PDF and include UI/database visuals.
