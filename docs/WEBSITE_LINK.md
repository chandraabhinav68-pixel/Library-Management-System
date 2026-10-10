# Book Haven website link

## Direct link

**[Open Book Haven](http://localhost:8080/)**

This address opens the app on the same computer where it is running. Each teammate gets their own copy and database. To start it, install JDK 21 or newer and Maven 3.9 or newer, then clone the GitHub repository. On Windows, double-click `run-book-haven.bat`. On macOS or Linux:

```bash
chmod +x run-book-haven.sh
./run-book-haven.sh
```

The first run downloads project dependencies, so it needs internet access. Keep the terminal open while using the site. Press **Ctrl+C** in that terminal to stop the app. The database stays in the local `data/` folder and is not shared with teammates.

## Sharing with reviewers

`localhost` points to the visitor's own computer, so this link will not open the website for someone viewing the GitHub repository. To let everyone use one shared website, follow [the public deployment guide](DEPLOYMENT.md) and use the deployed URL from the hosting service.
