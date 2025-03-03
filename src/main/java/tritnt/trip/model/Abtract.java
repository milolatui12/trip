package tritnt.trip.model;

import java.security.SecureRandom;

public abstract class Abtract {
    private static final String BASE62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom rnd = new SecureRandom();

    public String generateRandomBase62(int length) {
        StringBuilder res = new StringBuilder();

        for (int i = 0; i < length; i++) {
            res.append(BASE62.charAt(rnd.nextInt(BASE62.length())));
        }
        return res.toString();
    }
}
