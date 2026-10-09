MERGE INTO books (title, author, isbn, category, total_copies) KEY(isbn) VALUES
  ('The Midnight Library', 'Matt Haig', '9780525559474', 'Fiction', 4),
  ('Atomic Habits', 'James Clear', '9780735211292', 'Personal Growth', 3),
  ('The Hobbit', 'J. R. R. Tolkien', '9780007525492', 'Fantasy', 2),
  ('Educated', 'Tara Westover', '9780399590504', 'Memoir', 2),
  ('Project Hail Mary', 'Andy Weir', '9780593135204', 'Science Fiction', 3);

MERGE INTO members (name, email, phone) KEY(email) VALUES
  ('Asha Rao', 'asha@example.com', '9876543210'),
  ('Arjun Mehta', 'arjun@example.com', '9876543211');
