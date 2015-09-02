package com.willjiang.warthunderlive;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Will on 9/2/15.
 */
public class ParserTest extends ApplicationTest{

    public void testParser() throws IOException, JSONException{
        RequestMaker requestMaker = new RequestMaker();
        InputStream in = requestMaker.makeRequest();
        JsonParser parser = new JsonParser();
        parser.readJsonStream(in);
    }

}
