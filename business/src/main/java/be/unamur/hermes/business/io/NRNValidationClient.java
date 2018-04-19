package be.unamur.hermes.business.io;

import be.unamur.hermes.business.exception.NRNNotValidExceptions.NRNServiceAccessException;
import be.unamur.hermes.business.model.NRNValidation.NRNValidationModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.security.*;
import java.util.Base64;

public final class NRNValidationClient {

    public static NRNValidationModel getNRNValidationModel(String nRN) throws NRNServiceAccessException{
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
            };

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

            String encodedConnectionInformation = encodeConnectionInformation();

            HttpsURLConnection conn = getConn(nRN,encodedConnectionInformation);

            if (conn.getResponseCode() != 200) {
                throw new NRNServiceAccessException(
                        "Failed : HTTP error code : " + conn.getResponseCode());
            }

            NRNValidationModel nrnValidationModel = retrieveNRNValidationInformations(conn);

            conn.disconnect();
            return nrnValidationModel;
        } catch (IOException | NoSuchAlgorithmException | KeyManagementException e) {
            throw new NRNServiceAccessException("Unable to contact the NRN service : " + e.getMessage());
        }
    }

    /**
     * Create the HttpsConnection needed to acces the NRNValidationService
     * @param nRN The national register number we want to check
     * @param encodedConnectionInformation The encoded connection information
     * @return  A HttpsConnection
     * @throws IOException
     */
    private static HttpsURLConnection getConn(String nRN, String encodedConnectionInformation) throws IOException{
        URL url = new URL("https://91.121.217.193/validation/v1/nrn/" + nRN);

        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization","Basic " + encodedConnectionInformation);
        return conn;
    }


    // TODO set this in as environment variable or in a config file
    /**
     * Encode the user and password to access the NRNValidationService
     * @return The encoded information as a string
     */
    private static String encodeConnectionInformation(){
        String connectionInformation = "groupe8:cWolokyZUp";
        return Base64.getEncoder().encodeToString(connectionInformation.getBytes());
    }

    /**
     * Read the response input stream and convert it to a NRNValidationModel
     * @param conn The HttpsConnection use to contact the server
     * @return A NRNValidationModel
     * @throws IOException
     */
    private static NRNValidationModel retrieveNRNValidationInformations(HttpsURLConnection conn) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(sb.toString(),NRNValidationModel.class);
    }

}
