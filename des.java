import java.util.*;
import java.io.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.spec.*;

class DES {
    public static void encryptDecrypt(String key, int cipherMode, File in, File out) throws InvalidKeyException,
            NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IOException {
        FileInputStream fin = new FileInputStream(in);
        FileOutputStream fou = new FileOutputStream(out);
        DESKeySpec deskeyspec = new DESKeySpec(key.getBytes());
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        SecretKey secretkey = skf.generateSecret(deskeyspec);
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        if (cipherMode == Cipher.ENCRYPT_MODE) {
            cipher.init(Cipher.ENCRYPT_MODE, secretkey, SecureRandom.getInstance("SHA1PRNG"));
            CipherInputStream cis = new CipherInputStream(fin, cipher);
            write(cis, fou);
        } else if (cipherMode == Cipher.DECRYPT_MODE) {
            cipher.init(Cipher.DECRYPT_MODE, secretkey, SecureRandom.getInstance("SHA1PRNG"));
            CipherOutputStream cos = new CipherOutputStream(fou, cipher);
            write(fin, cos);
        }
    }

    public static void write(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[128];
        int nobr;
        while ((nobr = in.read(buffer)) != -1) {
            out.write(buffer, 0, nobr);
        }
        out.close();
        in.close();
    }

    public static void main(String arg[]) throws IOException, InvalidKeyException, NoSuchAlgorithmException,
            InvalidKeySpecException, NoSuchPaddingException, IOException {
        DataInputStream in = new DataInputStream(System.in);
        System.out.println("Enter Plane Text file location");
        String planeText = in.readLine();
        File fpt = new File(planeText);
        System.out.println("Enter a 16 bit Key");
        String key = in.readLine();
        System.out.println("Cypher text location");
        String cipher = in.readLine();
        File fct = new File(cipher);
        encryptDecrypt(key, Cipher.ENCRYPT_MODE, fpt, fct);
        System.out.println("Decript text location");
        String decrypt = in.readLine();
        File fdt = new File(decrypt);
        encryptDecrypt(key, Cipher.DECRYPT_MODE, fct, fdt);
    }
}