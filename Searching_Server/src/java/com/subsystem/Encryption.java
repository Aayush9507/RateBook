package com.subsystem;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


/**
 *
 * @author Aayush Goyal
 */
public class Encryption extends SymmetricKeyLoader{

    public Encryption() {
    }

    public Encryption(String symmetricKey) {
        super(symmetricKey);
    }
    
    public byte[] encrypt(byte[] plainBytes)
      { 
          byte[] encryptedBytes = new byte[0];
       
             try{   
                Cipher cipher = Cipher.getInstance("AES");
                SecretKeySpec mykey = new SecretKeySpec(symmetricKey.getBytes(), "AES");
		
//                byte[] mykey = Base64.decodeBase64(symmetricKey);
//                SecretKey newkey = new SecretKeySpec(mykey, 0, mykey.length, "AES"); 
                cipher.init(Cipher.ENCRYPT_MODE, mykey);
		encryptedBytes = cipher.doFinal(plainBytes);
                System.out.println("Message Encrypted successfully");
             }
             catch(Exception e)
             {System.out.println(e);}
	return encryptedBytes;
      }
    }
