package com.willjiang.warthunderlive.Network;

public class API {
    // URLs
    public static final String baseURL = "http://live.warthunder.com/api";
    public static final String unLoggedFeed = baseURL + "/feed/get_unlogged/";
    public static final String subscribedUserFeed = baseURL + "/feed/get_subscribes_users/";
    public static final String subscribedTagFeed = baseURL + "/feed/get_subscribes_tags/";
    public static final String loginURL = "https://login.gaijin.net/en/sso/login/procedure/";

    // Request paths
    public static final String posts_page = "page";
    public static final String posts_content = "content";
    public static final String posts_sort = "sort";
    public static final String posts_user = "user";
    public static final String posts_period = "period";

    // Login info
    public static String userIDKey = "userID";
    public static String userID = "0";
    public static String username = "username";
    public static String password = "password";

    // Json tags
    public static final String id = "id";
    public static final String type = "type";
    public static final String comments = "comments";
    public static final String views = "views";
    public static final String likes = "likes";
    public static final String is_liked = "isLiked";
    public static final String description = "description";
    public static final String timestamp = "created";
    public static final String language = "language";

    public static final String author = "author";
    public static final String author_id = "id";
    public static final String author_nickname = "nickname";
    public static final String author_avatar = "avatar";

    public static final String images = "images";
    public static final String image_src = "src";

    public static final String video_src= "video_src";
    public static final String video_info = "video_info";
    public static final String video_type = "image";
    public static final String video_image_src = "image";
}
