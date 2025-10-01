import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;

public class DropboxDemo {

    private static final String CLIENT_ID = "{{Client ID}}";
    private static final String CLIENT_SECRET = "{{CLIENT SECRET}}";
    private static final String REDIRECT_URI = "http://localhost";

    public static void main(String[] args) throws Exception {
        // Step 1: Authorization URL
        String authUrl = "https://www.dropbox.com/oauth2/authorize"
                + "?client_id=" + CLIENT_ID
                + "&response_type=code"
                + "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, "UTF-8")
                + "&token_access_type=offline"
                + "&scope=team_info.read members.read events.read";

        System.out.println("Open URL and authorize:\n" + authUrl);
        System.out.print("Paste authorization code here: ");
        String code = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();

        // Step 2: Exchange code for token
        String token = getAccessToken(code);
        if (token == null) {
            System.err.println("❌ Could not obtain token");
            return;
        }
        System.out.println("✅ Access Token: " + token);

        // Step 3: Fetch APIs
        System.out.println("\n=== Team Info ===");
        System.out.println(call("https://api.dropboxapi.com/2/team/get_info", "null", token));

        System.out.println("\n=== Sign-in Events ===");
        String payload = "{ \"category\": { \".tag\": \"logins\" } }";
        System.out.println(call("https://api.dropboxapi.com/2/team_log/get_events", payload, token));
    }

    /** Exchange auth code for token */
    private static String getAccessToken(String code) throws Exception {
        String params = "code=" + URLEncoder.encode(code, "UTF-8")
                + "&grant_type=authorization_code"
                + "&client_id=" + CLIENT_ID
                + "&client_secret=" + CLIENT_SECRET
                + "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI,"UTF-8");

        HttpURLConnection conn = (HttpURLConnection) new URL("https://api.dropboxapi.com/oauth2/token").openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        try (OutputStream os = conn.getOutputStream()) {
            os.write(params.getBytes(StandardCharsets.UTF_8));
        }

        String resp = read(conn);
        JSONObject json = new JSONObject(resp);
        return json.optString("access_token", null);
    }

    /** Generic Dropbox POST API call */
    private static String call(String url, String body, String token) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Authorization", "Bearer " + token);
        conn.setRequestProperty("Content-Type", "application/json");

        try (OutputStream os = conn.getOutputStream()) {
            os.write(body.getBytes(StandardCharsets.UTF_8));
        }
        return read(conn);
    }

    /** Read response (handles errors too) */
    private static String read(HttpURLConnection conn) throws IOException {
        InputStream is = conn.getResponseCode() < 400 ? conn.getInputStream() : conn.getErrorStream();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) sb.append(line);
            return sb.toString();
        }
    }
}

