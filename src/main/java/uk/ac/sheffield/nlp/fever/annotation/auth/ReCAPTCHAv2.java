package uk.ac.sheffield.nlp.fever.annotation.auth;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import uk.ac.sheffield.nlp.fever.annotation.Config;

public class ReCAPTCHAv2 {

    public class Response {
        public boolean success;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }
    }

    /**
     * Validates Google reCAPTCHA V2 or Invisible reCAPTCHA.
     * @param response reCAPTCHA response from client side. (g-recaptcha-response)
     * @return true if validation successful, false otherwise.
     */
    public static boolean isCaptchaValid(String response) {
        try {
            String url = "https://www.google.com/recaptcha/api/siteverify?"
                    + "secret=" + Config.getInstance().get("recaptcha.key")
                    + "&response=" + response;
            InputStream res = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(res, Charset.forName("UTF-8")));

            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }




            res.close();

            return (new Gson()).fromJson(sb.toString(),Response.class).isSuccess();
        } catch (Exception e) {
            return false;
        }
    }

}
