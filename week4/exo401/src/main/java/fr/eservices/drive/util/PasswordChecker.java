package fr.eservices.drive.util;

import org.springframework.stereotype.Component;

@Component
public interface PasswordChecker {

	String encode(String login, String password);

}
