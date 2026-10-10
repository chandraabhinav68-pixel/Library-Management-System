FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src ./src
RUN mvn -B -DskipTests package dependency:build-classpath -Dmdep.outputFile=target/dependency-classpath.txt

FROM eclipse-temurin:21-jre
WORKDIR /app
RUN mkdir -p /var/data
COPY --from=build /app/target/classes ./target/classes
COPY --from=build /app/target/dependency-classpath.txt ./target/dependency-classpath.txt
COPY --from=build /app/src/main/webapp ./src/main/webapp
ENV PORT=10000
ENV BOOKHAVEN_DB_URL="jdbc:h2:file:/var/data/bookhaven-library;DB_CLOSE_ON_EXIT=FALSE;DATABASE_TO_UPPER=FALSE"
EXPOSE 10000
CMD ["sh", "-c", "exec java -Dport=${PORT} -Dbookhaven.db.url=\"${BOOKHAVEN_DB_URL}\" -cp \"target/classes:$(cat target/dependency-classpath.txt)\" com.bookhaven.LibraryApplication"]
