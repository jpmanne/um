/**
 * @author Jp
 *
 */
package com.sciits.devops.poc.util;

import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.log4j.Logger;

public class PasswordEncy {

	Logger log = Logger.getLogger(PasswordEncy.class);
	// private static final String passPhrase = "CheckMETest1234!@#$";
	private static final String passPhrase = "JPSDSEJBLBSY@3256!@#$";
	Cipher ecipher;
	Cipher dcipher;
	private static PasswordEncy instance = null;

	// 8-byte Salt
	byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32, (byte) 0x56, (byte) 0x35, (byte) 0xE3,
			(byte) 0x03 };

	// Iteration count
	int iterationCount = 19;

	private PasswordEncy() {
		try {
			// Create the key
			KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount);
			// KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray());
			SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
			ecipher = Cipher.getInstance(key.getAlgorithm());
			dcipher = Cipher.getInstance(key.getAlgorithm());

			// Prepare the parameter to the ciphers
			AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

			// Create the ciphers
			ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
			dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
		} catch (Exception e) {
			log.error("Error creating PasswordEncry:", e);
		}
	}

	public String encrypt(String plainText) {
		try {
			// Encode the string into bytes using utf-8
			byte[] utf8 = plainText.getBytes("UTF8");

			// Encrypt
			byte[] enc = ecipher.doFinal(utf8);

			// Encode bytes to base64 to get a string
			return new sun.misc.BASE64Encoder().encode(enc);
		} catch (Exception e) {
			log.error("Error encrypt password:", e);
		}
		return null;
	}

	public String decrypt(String encText) {
		try {
			// Decode base64 to get bytes
			@SuppressWarnings("restriction")
			byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(encText);

			// Decrypt
			byte[] utf8 = dcipher.doFinal(dec);

			// Decode using utf-8
			return new String(utf8, "UTF8");
		} catch (Exception e) {
			log.error("Error decrypt password:", e);
		}

		return null;
	}

	public static synchronized PasswordEncy getInstance() {

		if (instance == null) {
			instance = new PasswordEncy();
		}
		return instance;
	}

	public static void main(String args[]) {
		System.out.println(PasswordEncy.getInstance().encrypt("sciits"));
	}
}