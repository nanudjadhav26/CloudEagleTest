# DropboxDemo Java CLI

A simple Java command-line application to authenticate with Dropbox via OAuth2, fetch the team/organization name, and retrieve team sign-in events using only Java Standard Library and org.json.

***

## Features

- Step-by-step OAuth2 (Authorization Code) flow
- Fetch Dropbox Business team info
- Fetch Dropbox sign-in events (audit logs)
- Usable without frameworks or heavyweight dependencies

***

## Prerequisites

- **Java JDK 8 or higher** (for `javac` and Java console input)
- **org.json** library ([download `json-20230227.jar`](https://search.maven.org/artifact/org.json/json/20230227/jar))

***

## Setup

1. **Clone or Download the Java File**

Download `DropboxDemo.java` and place it into your working directory.
2. **Download the org.json Library**

Place the downloaded `json-20230227.jar` in a `lib` directory:

```
YourProject/
  DropboxDemo.java
  lib/
    json-20230227.jar
```

3. **Update OAuth App Credentials**
    - Replace the sample `CLIENT_ID` and `CLIENT_SECRET` in the code with those from your Dropbox app (see below).

***

## How to Compile

Open a terminal in the project directory and run:

```bash
javac -cp ".;lib/json-20230227.jar" DropboxDemo.java   # Windows

# For Mac/Linux, use : instead of ;
# javac -cp ".:lib/json-20230227.jar" DropboxDemo.java
```


***

## How to Run

```bash
java -cp ".;lib/json-20230227.jar" DropboxDemo   # Windows

# For Mac/Linux:
# java -cp ".:lib/json-20230227.jar" DropboxDemo
```


***

## OAuth Flow

1. On running, the app prints an **authorization URL**.
2. Open the URL in your browser.
3. Authorize access with your Dropbox Business team account.
4. Copy the authorization code from the redirect (or from Dropbox UI).
5. Paste the code into the CLI when prompted.

***

## Output Example

```
Open URL and authorize:
https://www.dropbox.com/oauth2/authorize?client_id=****...
Paste authorization code here: [paste code]

✅ Access Token: sl.AAAA-BBBB....
=== Team Info ===
{"name":"Your Organization", ... }

=== Sign-in Events ===
{"events":[{"event_type":...}], ... }
```


***

## Dependencies

- **Standard Java SE libraries**
- **org.json** for minimal JSON parsing

To obtain **org.json**:

- Download from: [Maven Central](https://search.maven.org/artifact/org.json/json)
- If using Maven:

```xml
<dependency>
  <groupId>org.json</groupId>
  <artifactId>json</artifactId>
  <version>20230227</version>
</dependency>
```


***

## Configuration

- `CLIENT_ID`, `CLIENT_SECRET`: Set these from your Dropbox app dashboard
- `REDIRECT_URI`: Should match exactly with the URI set in your Dropbox app

***

## Troubleshooting

- **`javac` not recognized:** Install and add JDK to your system PATH.
- **400/401 errors:** Ensure the token is valid and you’re using a Dropbox Business account, not Personal.
- **JSON errors:** Ensure you have the correct org.json jar in your classpath.

***

## How it Works (Steps / Insights)

1. Prints a Dropbox OAuth2 authorization URL.
2. Reads the authorization code from user input.
3. Exchanges the code for an access token via Dropbox OAuth2 endpoint.
4. Uses that token to call the Dropbox Business team info and audit APIs.
5. Outputs JSON results directly to the console for easy verification.

***

## License

For assessment, demonstration, and educational use.

***

**CloudEagle Assessment – Dropbox Demo Java App**

