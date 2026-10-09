# API quick reference

All POST requests accept JSON and return JSON.

## Add a book

```http
POST /api/books
Content-Type: application/json
```

```json
{"title":"The Hobbit","author":"J. R. R. Tolkien","isbn":"9780007525492","category":"Fantasy","totalCopies":3}
```

## Register a member

```json
{"name":"Asha Rao","email":"asha@example.com","phone":"9876543210"}
```

## Issue a book

```json
{"bookId":1,"memberId":1}
```

## Return a book

`POST /api/loans/1/return`

Errors are JSON objects with a human-readable `error` field and an appropriate HTTP status.
