package com.willjiang.warthunderlive.Network;

/**
 * Created by will on 9/4/15.
 */
public class API {
    // URLs
    public static final String baseURL = "http://live.warthunder.com/api";
    public static final String unLogged = baseURL + "/feed/get_unlogged/";

    // Request paths
    public static final String posts_page = "page";
    public static final String posts_content = "content";
    public static final String posts_sort = "sort";
    public static final String posts_user = "user";
    public static final String posts_period = "period";

    // Json tags
    public static final String author = "author";
    public static final String type = "type";
    public static final String comments = "comments";
    public static final String views = "views";
    public static final String likes = "likes";
    public static final String is_liked = "isLiked";
    public static final String description = "description";
    public static final String images = "images";
    public static final String timestamp = "created";

    public static final String author_id = "id";
    public static final String author_nickname = "nickname";
    public static final String author_avatar = "avatar";

    public static final String image_src = "src";
}
