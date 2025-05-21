package ubb.ro.socialnetworkgui.UsefulTools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordEncryptor {
    // Method to generate a salt
    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    // Method to hash the password using SHA-256 and salt
    public static String hashPassword(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());

            // Encode hashed password and salt as Base64
            byte[] combined = new byte[hashedPassword.length + salt.length];
            System.arraycopy(hashedPassword, 0, combined, 0, hashedPassword.length);
            System.arraycopy(salt, 0, combined, hashedPassword.length, salt.length);

            return Base64.getEncoder().encodeToString(combined);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to verify the entered password against the stored hashed password
    public static boolean verifyPassword(String password, String hashedPassword, byte[] salt) {
        String newHash = hashPassword(password, salt);
        return hashedPassword.equals(newHash);
    }
}
