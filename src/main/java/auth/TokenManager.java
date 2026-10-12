package auth;

public final class TokenManager {

    private static final ThreadLocal<String> TOKEN = new ThreadLocal<>();

    private TokenManager() {
    }

    public static void setToken(String token) {

        TOKEN.set(token);
    }

    public static String getToken() {

        return TOKEN.get();
    }

    public static void clearToken() {

        TOKEN.remove();
    }
}

