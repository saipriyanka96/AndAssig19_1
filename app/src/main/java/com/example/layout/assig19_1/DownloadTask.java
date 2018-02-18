package com.example.layout.assig19_1;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by Pri on 8/30/2017.
 */

public class DownloadTask extends AsyncTask<String,Void,String> {
    /**AsyncTask is designed to be a helper class around Thread and Handler and does not constitute a generic threading framework
     Params, the type of the parameters sent to the task upon execution.
     Progress, the type of the progress units published during the background computation.
     Result, the type of the result of the background computation.**/
    String result="";
    URL url;
    //A URLConnection with support for HTTP-
    //A Uniform Resource Locator, colloquially termed a web address, is a reference to a web resource that specifies its location on a computer network and a mechanism for retrieving it
    HttpURLConnection httpURLConnection=null;
    @Override
    //Override this method to perform a computation on a background thread
    //Parameters
   // params	Params: The parameters of the task.
     //       Returns
    //Result	A result, defined by the subclass of this task.
    protected String doInBackground(String... urls) {

        try {
            url= new URL(urls[0]);
            //it is taking the index of urls in the string of urls
            //Returns a URLConnection instance that represents a connection to the remote object referred to by the URL.
            httpURLConnection= (HttpURLConnection) url.openConnection();
            InputStream in= httpURLConnection.getInputStream();
            //input stream is used to read data from a source
            InputStreamReader reader= new InputStreamReader(in);
            //object created
            int data= reader.read();
            //this mewthod reads the next byte of the data from the input stram
            while (data!=-1){
                //if data ia not equal to -1
                //this will concatenate the char
                char current= (char)data;
                result+=current;
                //autoincrement
                data=reader.read();
                //reads the next byte
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
//it prints a stack trace for this throwable objrct on the output stream
//returns the null value
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        //runs the ui thread after doInbackground the specfic result is the value returned by doInbackground
        //returns the result
        super.onPostExecute(result);
        try {
            JSONObject jsonObject= new JSONObject(result);
            //a modified set of name/value mappings
            //object will take the result
            //returns the value mapped by name if it exists
            JSONObject main=new JSONObject(jsonObject.getString("main"));
            //from main we get the temperature and object gives place name
            String temperature = main.getString("temp");
            String placeName = jsonObject.getString("name");
//from the mainactivity we take the variables and set the values to the respective one
            MainActivity.place.setText(placeName);
            MainActivity.temp.setText(temperature);
            MainActivity.temp.setText(temperature);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
