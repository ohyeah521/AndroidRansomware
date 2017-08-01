package com.h;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class CryptoUtil {
    private static String strDefaultKey = "national";
    private Cipher decryptCipher;
    private Cipher encryptCipher;

    public CryptoUtil() throws Exception {
        this(strDefaultKey);
    }

    public CryptoUtil(String str) {
        encryptCipher = null;
        decryptCipher = null;
        try {
            Key key = getKey(str.getBytes());
            encryptCipher = Cipher.getInstance("DES");
            encryptCipher.init(1, key);
            decryptCipher = Cipher.getInstance("DES");
            decryptCipher.init(2, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String byteArr2HexStr(byte[] bArr) throws Exception {
        int length = bArr.length;
        StringBuilder stringBuffer = new StringBuilder(length * 2);
        for (int i : bArr) {
            int i2 = bArr[i];
            while (i2 < 0) {
                i2 += 256;
            }
            if (i2 < 16) {
                stringBuffer.append('0');
            }
            stringBuffer.append(Integer.toString(i2, 16));
        }
        return stringBuffer.toString();
    }

    public static byte[] hexStr2ByteArr(String str) throws Exception {
        byte[] bytes = str.getBytes();
        int length = bytes.length;
        byte[] bArr = new byte[(length / 2)];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) Integer.parseInt(new String(bytes, i, 2), 16);
        }
        return bArr;
    }

    private Key getKey(byte[] bArr) throws Exception {
        byte[] bArr2 = new byte[8];
        int i = 0;
        while (i < bArr.length && i < bArr2.length) {
            bArr2[i] = bArr[i];
            i++;
        }
        return new SecretKeySpec(bArr2, "DES");
    }

    public String decrypt(String str) throws Exception {
        return new String(decrypt(hexStr2ByteArr(str)));
    }

    public byte[] decrypt(byte[] bArr) throws Exception {
        return decryptCipher.doFinal(bArr);
    }

    public String encrypt(String str) throws Exception {
        return byteArr2HexStr(encrypt(str.getBytes()));
    }

    public byte[] encrypt(byte[] bArr) throws Exception {
        return encryptCipher.doFinal(bArr);
    }
}
