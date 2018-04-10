package be.unamur.hermes.business.io;

import be.unamur.hermes.business.model.NRNValidationModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public final class NRNValidation {

    public NRNValidationModel validate(String NRN){
        try {
            NRNValidationModel nrnValidationModel;
            URL url = new URL("https://91.121.217.193/validation/v1/nrn/%7bNRN%7d");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            ObjectMapper mapper = new ObjectMapper();
            nrnValidationModel = mapper.readValue(url, NRNValidationModel.class);

            conn.disconnect();
            return nrnValidationModel;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
