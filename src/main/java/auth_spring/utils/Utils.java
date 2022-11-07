package auth_spring.utils;

import java.time.Instant;
import java.util.UUID;

public class Utils {

    public static String generateUniqueToken() {
        StringBuilder token = new StringBuilder();
        long currentTimeInMilisecond = Instant.now().toEpochMilli();

        return token.append(currentTimeInMilisecond).append("-")
                .append(UUID.randomUUID().toString()).toString();
    }
}
