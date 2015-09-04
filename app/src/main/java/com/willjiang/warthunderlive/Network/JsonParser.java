package com.willjiang.warthunderlive.Network;

import android.util.JsonReader;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Will on 9/1/15.
 */

public class JsonParser {

    public List readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readPostArray(reader);
        } finally {
            reader.close();
        }
    }

    public List readPostArray(JsonReader reader) throws IOException {
        List posts = new ArrayList();

        reader.beginArray();
        while (reader.hasNext()) {
            posts.add(readPost(reader));
        }
        reader.endArray();
        return posts;
    }

    public HashMap readPost (JsonReader reader) throws  IOException {
        HashMap post = new HashMap();
        reader.beginObject();

        post.put("description", "");
        post.put("images", new ArrayList());

        while (reader.hasNext()) {
            String key = reader.nextName();
            if (key.equals("description")) {
                post.put(key, reader.nextString());
            } else if (key.equals("images")) {
                post.put(key, readImages(reader));
            } else {
                reader.skipValue();
            }
        }

        reader.endObject();
        return post;
    }

    public List readImages(JsonReader reader) throws IOException {
        List images = new ArrayList();
        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            while (reader.hasNext()) {
                String key = reader.nextName();
                if (key.equals("src")) {
                    images.add(reader.nextString());
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
        }
        reader.endArray();
        return images;
    }


}
