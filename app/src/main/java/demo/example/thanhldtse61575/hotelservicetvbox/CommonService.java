package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by ThanhLDTSE61575 on 2/14/2017.
 */

public class CommonService {
    class GetDataFromServer extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            String link  = params[0];
            try {
                URL url = new URL(link);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
        }
    }

    class  SendDataToServer extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            String link = params[0];
            String urlParameters = params[1];
            try {
                //Create connection
                URL url = new URL(link);
                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
                connection.setRequestProperty("Content-Language", "en-US");

                connection.setUseCaches (false);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                //Send request
                DataOutputStream wr = new DataOutputStream (connection.getOutputStream ());
                wr.writeBytes (urlParameters);
                wr.flush ();
                wr.close ();

                //Get Response
            /*InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            System.out.print(connection.getResponseCode());
            rd.close();*/
                return connection.getResponseCode()+"";

            } catch (Exception e) {

                e.printStackTrace();
                return "-100";

            } finally {

                if(connection != null) {
                    connection.disconnect();
                }
            }
        }
    }

    //getData form server, output is JSON
    //using like this:  List<Service> acc = new Gson().fromJson(response, new TypeToken<List<Service>>() {}.getType()); for pare to obejct
    //Service: class
    //response: input JSON
    public String getData(String link){
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(link);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                bufferedReader.close();
                return stringBuilder.toString();
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    //send Data to server
    //using like this: String retu = new Gson().toJson(acc); for pare to JSON
    //retu: JSON string output
    //acc : input object
    public int sendData(String link, ContentValues contentValues){
        HttpURLConnection connection = null;
        try {
            //Create connection
            URL url = new URL(link);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            //connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches (false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //Send request
            String a = getQuery(contentValues);
            DataOutputStream wr = new DataOutputStream (connection.getOutputStream ());
            wr.writeUTF (a);
//            OutputStream os = connection.getOutputStream();
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
//
//            writer.write(a);
//
//            writer.flush ();
//            writer.close ();
//            os.close();

            wr.flush();
            wr.close();
            connection.connect();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            System.out.print(connection.getResponseCode());
            rd.close();
            return connection.getResponseCode();

        } catch (Exception e) {

            e.printStackTrace();
            return -100;

        } finally {

            if(connection != null) {
                connection.disconnect();
            }
        }
    }

    private String getQuery(ContentValues contentValues) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, Object> entry : contentValues.valueSet()) {
            if (first)
                first = false;
            else
                sb.append("&");

            sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            sb.append("=");
            sb.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
        }
        return sb.toString();
    }
}
