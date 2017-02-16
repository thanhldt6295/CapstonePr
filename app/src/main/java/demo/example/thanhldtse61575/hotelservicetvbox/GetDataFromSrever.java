package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.Service;

/**
 * Created by ThanhLDTSE61575 on 2/16/2017.
 */

public class GetDataFromSrever extends AsyncTask<String, Void, Integer>{
    private Context ctx;
    private List<Service> list;

    GetDataFromSrever(Context ctx, List<Service> list){
        this.ctx = ctx;
        this.list = list;
    }

    @Override
    protected Integer doInBackground(String... params) {
        CommonService commonService = new CommonService();
        int returnva = commonService.sendData(params[0],params[1]);
        return returnva;
    }

    protected void onPostExecute(Integer response) {
        //String retu = new Gson().toJson(acc);
    }
}
