package fr.eservices.drive.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@Component
@Qualifier("hmac")
public class HmacChecker implements PasswordChecker {
	
	@Override
	public String encode(String login, String password) {
		try {
			Mac mac = Mac.getInstance("HmacSHA1");
			SecretKeySpec secret_key = new SecretKeySpec(login.getBytes("UTF-8"),"HmacSHA256");
			mac.init(secret_key);
			return Base64.encode(mac.doFinal(password.getBytes("UTF-8")));
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return null;
	}

}
