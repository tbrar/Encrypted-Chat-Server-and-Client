import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.KeyAgreement;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

public class KeyExchange {
	SecureRandom random;
	KeyPairGenerator keyGen;
	DHPrivateKey privateKey;
	DHPublicKey publicKey;
	KeyPair keys;
	BigInteger pBig;
	BigInteger gBig;
	DHParameterSpec spec;
	KeyAgreement agreement;
	byte[] secret;
	int bits;
	
	public KeyExchange(int size) {
		bits = size;
		random = new SecureRandom();
	}
	
	public Key getPrivateKey() {
		return privateKey;
	}
	
	public Key getPublicKey() {
		return publicKey;
	}
	
	public BigInteger getP(){
		return pBig;
	}
	
	public BigInteger getG(){
		return gBig;
	}
	
	public byte[] getSecret() {
		return secret;
	}
	
	public void setPG(BigInteger p, BigInteger g) {
		pBig = p;
		gBig = g;
		init();
		return;
	}
	
	public void init() {
		spec = new DHParameterSpec(pBig, gBig);
		try {
			keyGen = KeyPairGenerator.getInstance("DiffieHellman");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("No Such Algorithm as DiffieHellman");
		}
		try {
			keyGen.initialize(spec, random);
		} catch (InvalidAlgorithmParameterException e) {
			System.out.println("Invalid Algorithm");
		}
		keys = keyGen.generateKeyPair();
		privateKey = (DHPrivateKey) keys.getPrivate();
		publicKey = (DHPublicKey) keys.getPublic();
		try {
			agreement = KeyAgreement.getInstance("DiffieHellman");
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			agreement.init(privateKey);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void generatePG() {
		pBig = new BigInteger(bits, 4, random);
		gBig = new BigInteger(224, 4, random);
		init();
		return;
	}
	
	public void generateSecret(Key key) {
		try {
			agreement.doPhase(key, true);
		} catch (InvalidKeyException e) {
			System.out.print("Invalid Key!");
		} catch (IllegalStateException e) {
			System.out.print("Illegal State!");
		}
		secret = agreement.generateSecret();
	}
}
