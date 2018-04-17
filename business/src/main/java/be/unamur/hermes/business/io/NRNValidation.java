package be.unamur.hermes.business.io;

import be.unamur.hermes.business.model.NRNValidationModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.security.*;
import java.security.cert.CertificateException;

public final class NRNValidation {

    public NRNValidationModel validate(String NRN){
        try {
            KeyStore clientStore = KeyStore.getInstance("PKCS12");
            clientStore.load(new FileInputStream("nrnvalidator_domain_public.crt"), "testPass".toCharArray());

            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(clientStore, "testPass".toCharArray());
            KeyManager[] kms = kmf.getKeyManagers();

            KeyStore trustStore = KeyStore.getInstance("JKS");
            trustStore.load(new FileInputStream("cacerts"), "changeit".toCharArray());

            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(trustStore);
            TrustManager[] tms = tmf.getTrustManagers();

            SSLContext sslContext = null;
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kms, tms, new SecureRandom());

            String connectionInformation = "groupe8:cWolokyZUp";
            String encodedConnectionInformation = Base64.getEncoder().encodeToString(connectionInformation.getBytes());
            System.out.println("encoded " + encodedConnectionInformation);

            NRNValidationModel nrnValidationModel;
            URL url = new URL("https://91.121.217.193/validation/v1/nrn/" + NRN);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization","Basic " + encodedConnectionInformation);

            if (conn.getResponseCode() != 200) {
                System.out.println(conn.getResponseMessage());
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            System.out.println("response code == 200");

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
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

}
