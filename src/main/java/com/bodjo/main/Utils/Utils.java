package com.bodjo.main.Utils;

import com.bodjo.main.Constants;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class Utils {
    public static String dbUser;
    public static String dbPassword;
    public static String path;

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
