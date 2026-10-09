# Book Haven website link

## Direct link

**[Open Book Haven](http://localhost:8080/)**

This address opens the app on the same computer where it is running. Start the project from its folder with:

```bash
mvn clean compile exec:java
```

Keep that terminal open while using the site. Press **Ctrl+C** in that terminal to stop the app.

## Sharing with reviewers

`localhost` points to the visitor's own computer, so this link will not open the website for someone viewing the GitHub repository. To share a public website, deploy the application to a hosting service and replace the local address above with the deployed URL.
