package be.unamur.hermes.common.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {

    public static String encode(String raw) {
	return new BCryptPasswordEncoder().encode(raw);
    }

    public static void main(String[] args) {
	System.out.println(encode(args[0]));
    }

}
