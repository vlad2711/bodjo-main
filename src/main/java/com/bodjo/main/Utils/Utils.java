package com.bodjo.main.Utils;

import com.bodjo.main.Constants;
import org.apache.tomcat.util.codec.binary.Base64;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Utils {
    public static String dbUser;
    public static String dbPassword;
    public static String path;
    public static char[] validSymbols = new char[]{'1','2', '3', '4', '5' ,'6', '7', '8', '9', '0','q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o','p','a','s','d','f','g','h','j','k','l','z','x','c','v','b','n','m', 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P','A', 'S','D','F','G','H','J','K','L','Z','X','C','V','B','N','M','_','-'};

    public static String getFile(String fileName){
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(fileName);
        return convertStreamToString(is);
    }

    private static String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static String encrypt(final String text) {
        return Base64.encodeBase64String(xor(text.getBytes()));
    }

    public static String decrypt(final String hash) {
        return new String(xor(Base64.decodeBase64(hash.getBytes())), StandardCharsets.UTF_8);
    }

    public static boolean checkValid(String string){
        return string.matches("^[a-zA-Z0-9_-]+$");
    }
    private static byte[] xor(final byte[] input) {
        final byte[] output = new byte[input.length];
        final byte[] secret = Constants.KEY.getBytes();
        int spos = 0;
        for (int pos = 0; pos < input.length; ++pos) {
            output[pos] = (byte) (input[pos] ^ secret[spos]);
            spos += 1;
            if (spos >= secret.length) {
                spos = 0;
            }
        }
        return output;
    }
}
