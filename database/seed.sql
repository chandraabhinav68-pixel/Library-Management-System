INSERT INTO books (title, author, isbn, category, total_copies)
SELECT 'The Midnight Library', 'Matt Haig', '9780525559474', 'Fiction', 4
WHERE NOT EXISTS (SELECT 1 FROM books WHERE isbn = '9780525559474');
INSERT INTO books (title, author, isbn, category, total_copies)
SELECT 'Atomic Habits', 'James Clear', '9780735211292', 'Personal Growth', 3
WHERE NOT EXISTS (SELECT 1 FROM books WHERE isbn = '9780735211292');
INSERT INTO books (title, author, isbn, category, total_copies)
SELECT 'The Hobbit', 'J. R. R. Tolkien', '9780007525492', 'Fantasy', 2
WHERE NOT EXISTS (SELECT 1 FROM books WHERE isbn = '9780007525492');
INSERT INTO books (title, author, isbn, category, total_copies)
SELECT 'Educated', 'Tara Westover', '9780399590504', 'Memoir', 2
WHERE NOT EXISTS (SELECT 1 FROM books WHERE isbn = '9780399590504');
INSERT INTO books (title, author, isbn, category, total_copies)
SELECT 'Project Hail Mary', 'Andy Weir', '9780593135204', 'Science Fiction', 3
WHERE NOT EXISTS (SELECT 1 FROM books WHERE isbn = '9780593135204');

INSERT INTO members (name, email, phone)
SELECT 'Asha Rao', 'asha@example.com', '9876543210'
WHERE NOT EXISTS (SELECT 1 FROM members WHERE email = 'asha@example.com');
INSERT INTO members (name, email, phone)
SELECT 'Arjun Mehta', 'arjun@example.com', '9876543211'
WHERE NOT EXISTS (SELECT 1 FROM members WHERE email = 'arjun@example.com');
