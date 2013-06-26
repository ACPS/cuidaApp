/**
 * 
 */
package com.nerdcore.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import org.apache.commons.lang3.StringUtils;

import com.nerdcore.logs.Trace;

/**
 * @author Jorge
 * 
 */
public class SHA1Utils {

	private static final String TAG = "class SHA1Utils";

	public static String random128bytes() {
		try {
			SecureRandom secure_random = null;
			if (android.os.Build.VERSION.SDK_INT >= 17) {
				try {
					secure_random = SecureRandom.getInstance("SHA1PRNG",
							"Crypto");
				} catch (NoSuchProviderException e) {
					Trace.e(TAG, e.getMessage());
				}
			} else {
				secure_random = SecureRandom.getInstance("SHA1PRNG");
			}
			byte[] random = new byte[128];
			StringBuilder sb = new StringBuilder();
			secure_random.nextBytes(random);
			for (byte b : random) {
				sb.append(String.format("%02X", b));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			Trace.e(TAG, e.getMessage());
		}
		return null;
	}

	public static String sha1Hash(String[] data) {
		String hash = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			String join = StringUtils.join(data);
			byte[] bytes = join.getBytes("UTF-8");
			digest.update(bytes, 0, bytes.length);
			bytes = digest.digest();
			StringBuilder sb = new StringBuilder();
			for (byte b : bytes) {
				sb.append(String.format("%02X", b));
			}
			hash = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			Trace.e(TAG, e.getMessage());
		} catch (UnsupportedEncodingException e) {
			Trace.e(TAG, e.getMessage());
		}
		return hash;
	}
}
