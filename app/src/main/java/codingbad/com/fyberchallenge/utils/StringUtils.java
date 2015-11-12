package codingbad.com.fyberchallenge.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by ayi on 11/12/15.
 */
public class StringUtils {
    public static String convertToString(BufferedReader bufferedReader) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }

        }

        return stringBuilder.toString();
    }

    public static String convertToString(InputStream is) {
        return convertToString(new BufferedReader(new InputStreamReader(is)));
    }

}
