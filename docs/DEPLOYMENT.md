# Public demo deployment

This project can run as one shared public demo using a free Render web service and a free Neon PostgreSQL database. Render runs the Java website. Neon stores books, readers and loan records so they remain available when the website service sleeps or redeploys.

## 1. Create the shared database

1. Create a Neon account and a PostgreSQL project on its free plan.
2. Copy the database host, database name, username and password from the Neon dashboard. Keep the password private.
3. The connection URL for Render should use this form, with your actual host and database name:

   ```text
   jdbc:postgresql://YOUR-NEON-HOST/YOUR-DATABASE?sslmode=require
   ```

If Neon offers a pooled connection, use its host for this small shared demo. The application creates the tables and inserts the starter books and members on first startup.

## 2. Create the public website

1. Sign in to Render and choose **New > Blueprint**.
2. Connect the public `Library-Management-System` GitHub repository. Render reads the root `render.yaml` and builds the Dockerfile.
3. Select the free web service option. When prompted, enter the three Neon values as environment variables:
   - `BOOKHAVEN_DB_URL`: the JDBC URL above.
   - `BOOKHAVEN_DB_USER`: the Neon database username.
   - `BOOKHAVEN_DB_PASSWORD`: the Neon database password.
4. Create the Blueprint and wait until the service reports that it is live.
5. Open the `onrender.com` address shown in Render. Share that address with teammates and reviewers.

## Free service behavior

Render free web services sleep after 15 minutes without traffic. The next visit starts the service again and can take about a minute. Neon Free currently includes 1 GB database storage per project and limited compute and network usage. Check the provider dashboards for current quotas. The website can be free to run within the current limits, but usage overages or provider changes may affect availability.

The library records live in the Neon database, so a Render restart does not erase them. Every visitor uses the same shared records.

## Public demo access

This project has no login or staff roles. Anyone with the public link can add or change demo books, members and loans. Use sample information only. Do not enter real contact details or other private information. Add authentication before using the app for a real library.

## Local Docker run

With Docker installed, build and run a local copy with its own persistent H2 database:

```bash
docker build -t book-haven .
docker run --rm -p 8080:8080 -e PORT=8080 -e BOOKHAVEN_DB_URL='jdbc:h2:file:/var/data/bookhaven-library;DB_CLOSE_ON_EXIT=FALSE;DATABASE_TO_UPPER=FALSE' -v book-haven-data:/var/data book-haven
```

Then open `http://localhost:8080/`. Stop the container with Ctrl+C.
