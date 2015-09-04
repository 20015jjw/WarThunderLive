package com.willjiang.warthunderlive.Network;

import android.content.Context;
import android.util.JsonReader;

import com.willjiang.warthunderlive.R;

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

    Context mContext;

    public JsonParser (Context context) {
        this.mContext = context;
    }

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

        post.put(API.description, "");
        post.put(API.images, new ArrayList());

        while (reader.hasNext()) {
            String key = reader.nextName();
            if (key.equals(API.description)) {
                post.put(key, reader.nextString());
            } else if (key.equals(API.images)) {
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
                if (key.equals(API.image_src)) {
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
