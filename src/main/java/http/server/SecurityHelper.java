package http.server;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SecurityHelper {
    public String hashWith256(String textToHash) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            byte[] byteOfTextToHash = textToHash.getBytes(StandardCharsets.UTF_8);
            byte[] hashedByteArray = digest.digest(byteOfTextToHash);
            return Base64.getEncoder().encodeToString(hashedByteArray);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
