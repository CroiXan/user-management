FROM openjdk:21-ea-24-oracle

WORKDIR /app
COPY target/user-1.0-SNAPSHOT.jar app.jar
COPY Wallet_CKVY7SUY33QB5HUU /app/oracle_wallet
EXPOSE 8080

CMD [ "java", "-jar", "app.jar" ]