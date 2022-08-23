package com.paopao.user.utils;

import javax.servlet.ServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @Author 111
 * @Date 2022/8/14 18:23
 * @Description
 */
public class HttpHelper {

    public static String getBodyString(ServletRequest request) {

        StringBuilder sb = new StringBuilder();

        InputStream inputStream = null;

        BufferedReader reader = null;
        try {
            inputStream = request.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);

            }
        } catch (IOException e) {
//            LogUtils.error(e);
            throw new RuntimeException(e);
        } finally {

            if (inputStream != null) {

                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return sb.toString()/*.replaceAll(" ","")*/;
    }

}
