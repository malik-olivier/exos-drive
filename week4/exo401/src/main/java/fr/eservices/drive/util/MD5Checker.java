package fr.eservices.drive.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;

@Component
@Qualifier("md5")
public class MD5Checker implements PasswordChecker {

	@Override
	public String encode(String login, String password) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update((login+password).getBytes());
			return Base64.encode(messageDigest.digest());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
