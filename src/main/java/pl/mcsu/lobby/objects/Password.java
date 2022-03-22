package pl.mcsu.lobby.objects;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Password {

    private String password;
    private final byte[] salt;

    public Password(String password) {
        this.password = password;
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[16];
        secureRandom.nextBytes(bytes);
        this.salt = bytes;
    }

    public Password(String password, byte[] salt) {
        this.password = password;
        this.salt = salt;
    }

    public String getPassword() {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(salt);
            byte[] bytes = messageDigest.digest(password.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            for (byte aByte : bytes) {
                stringBuilder.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            this.password = stringBuilder.toString();
        } catch (NoSuchAlgorithmException exception) {
            exception.printStackTrace();
        }
        return password;
    }

    public byte[] getSalt() {
        return salt;
    }

}
