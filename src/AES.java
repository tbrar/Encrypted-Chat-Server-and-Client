import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class AES {
	SecureRandom random = new SecureRandom();
	SecretKeySpec spec;
	byte[] key;
	MessageDigest sha;
	IvParameterSpec ivSpec;
	byte[] iv = new byte[16];
	Cipher decryptCipher;
	Cipher encryptCipher;
	
	public AES(byte[] secret) {
		key = secret;
		try {
			sha = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		key = sha.digest(secret);
		spec = new SecretKeySpec(key, "AES");
       try {
		decryptCipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		encryptCipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	} catch (NoSuchAlgorithmException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (NoSuchPaddingException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
		
	}
	
	public void generateIV() {
		random.nextBytes(iv);
		ivSpec = new IvParameterSpec(iv);
		try {
			decryptCipher.init(Cipher.DECRYPT_MODE, spec, ivSpec);
			encryptCipher.init(Cipher.ENCRYPT_MODE, spec, ivSpec);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setIV(byte[] iv) {
		this.iv = iv;
		ivSpec = new IvParameterSpec(iv);
		try {
			decryptCipher.init(Cipher.DECRYPT_MODE, spec, ivSpec);
			encryptCipher.init(Cipher.ENCRYPT_MODE, spec, ivSpec);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public byte[] getIV() {
		return iv;
	}
	
	public String encrypt(String message) {
		try {
			Encoder base64Encoder = Base64.getEncoder();
			byte[] unencodedEncrypted = encryptCipher.doFinal(message.getBytes());
			String encrypted = base64Encoder.encodeToString(unencodedEncrypted);
			return encrypted;
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public String decrypt(String message) {
		try {
			Decoder base64Decoder = Base64.getDecoder();
			byte[] decoded = base64Decoder.decode(message);
			byte[] decrypted = decryptCipher.doFinal(decoded);
			return new String(decrypted);
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
