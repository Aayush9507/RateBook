package com.subsystem;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Aayush goyal
 */
public class Decryption extends SymmetricKeyLoader{

    public Decryption() {
    }

    Decryption(String symmetricKey) {
        super(symmetricKey);
    }
    
    public byte[] decrypt(byte[] encryptedBytes)
        {   byte[] decryptedBytes = new byte[0];
		try{	
                
                SecretKeySpec mykey = new SecretKeySpec(symmetricKey.getBytes(), "AES");
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.DECRYPT_MODE, mykey);
                decryptedBytes = cipher.doFinal(encryptedBytes);
                System.out.println("Message Decrypted successfully");
		
                }
                catch(Exception e){
                System.out.println("Error in decryption");}
                
            return decryptedBytes;
	}
    
    
}
