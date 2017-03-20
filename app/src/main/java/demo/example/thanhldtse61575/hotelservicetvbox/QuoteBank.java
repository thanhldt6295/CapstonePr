package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThanhLDTSE61575 on 2/28/2017.
 */

public class QuoteBank {
    private Context mContext;

    public QuoteBank(Context context) {
        this.mContext = context;
    }

    public List<String> readLine(String path) throws FileNotFoundException {
        List<String> mLines = new ArrayList<>();

        AssetManager am = mContext.getAssets();
        //FileInputStream fileInputStream = new FileInputStream(new File(Environment.getExternalStorageDirectory()+"/ARoomID", path));

        try {
            InputStream is = am.open(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//            byte[] b=new byte[100];
//            fileInputStream.read(b);
            String line;
//            line = new String(b);
//            fileInputStream.close();

            while ((line = reader.readLine()) != null)
                mLines.add(line);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mLines;
    }
}
