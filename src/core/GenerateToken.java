package core;

import java.util.UUID;

/**
 * Class for handling token generation.
 * @author Fraser Grandfield
 * @version 1.0
 * @since 19/06/20
 */
public class GenerateToken {

    /**
     * Generate a random token.
     * @return String token.
     */
    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}
