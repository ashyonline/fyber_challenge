package codingbad.com.fyberchallenge.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ayi on 11/15/15.
 */
public class StringUtils {
    public static String convertToString(BufferedReader br) {
        StringBuilder sb = new StringBuilder();

        try {
            String line;
            try {
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException var12) {
                var12.printStackTrace();
            }
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException var11) {
                    var11.printStackTrace();
                }
            }

        }

        return sb.toString();
    }

    public static String calculateSha1(String text) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(text.getBytes("iso-8859-1"), 0, text.length());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }

        byte[] sha1hash = messageDigest.digest();
        return convertToHex(sha1hash);
    }

    private static String convertToHex(byte[] data) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int twoHalfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    stringBuffer.append((char) ('0' + halfbyte));
                } else {
                    stringBuffer.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = data[i] & 0x0F;
            } while (twoHalfs++ < 1);
        }
        return stringBuffer.toString();
    }


    public static String convertToString(InputStream is) {
        return convertToString(new BufferedReader(new InputStreamReader(is)));
    }
}
