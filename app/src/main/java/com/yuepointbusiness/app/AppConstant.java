package com.yuepointbusiness.app;

/**
 * des:
 * Created by xsf
 * on 2016.09.10:44
 */
public class AppConstant {

    public static final String HOME_CURRENT_TAB_POSITION = "HOME_CURRENT_TAB_POSITION";
    public static final String MENU_SHOW_HIDE = "MENU_SHOW_HIDE";

    /* 新闻*/
    public static final String NEWS_ID = "news_id";
    public static final String NEWS_TYPE = "news_type";
    public static final String CHANNEL_POSITION = "channel_position";
    public static final String CHANNEL_MINE = "CHANNEL_MINE";
    public static final String CHANNEL_MORE = "CHANNEL_MORE";
    public static final String CHANNEL_SWAP = "CHANNEL_SWAP";
    public static final String NEWS_CHANNEL_CHANGED = "NEWS_CHANNEL_CHANGED";

    /* 视频*/
    public static final String VIDEO_TYPE = "VIDEO_TYPE";

    public static String NEWS_LIST_TO_TOP = "NEWS_LIST_TO_TOP";//列表返回顶部
    public static String ZONE_PUBLISH_ADD = "ZONE_PUBLISH_ADD";//发布说说

    public static String NEWS_POST_ID = "NEWS_POST_ID";//新闻详情id
    public static String NEWS_LINK = "NEWS_LINK";
    public static String NEWS_TITLE = "NEWS_TITLE";

    public static final String PHOTO_DETAIL_IMGSRC = "photo_detail_imgsrc";
    public static final String PHOTO_DETAIL = "photo_detail";
    public static final String PHOTO_TAB_CLICK = "PHOTO_TAB_CLICK";

    public static final String NEWS_IMG_RES = "news_img_res";
    public static final String TRANSITION_ANIMATION_NEWS_PHOTOS = "transition_animation_news_photos";

    //my
    public static final String MY_PHONE_KEY = "my_phone";
    //渠道Id
    public static final String CHANNEL_ID = "huifx001";
    public static final String bundle_ID = "com.yingshi.app";

    public static final String RSA_PRIVATE = "-----BEGIN PRIVATE KEY-----\n" +
            "MIIJQQIBADANBgkqhkiG9w0BAQEFAASCCSswggknAgEAAoICAQC4btgE/olvz/QM\n" +
            "d7Kn0lOctbcHOiilatciTmevS0MeCjSWP1LiunYAnuW9VSV8SF2TYmWWBrPR5fvq\n" +
            "9zOvfOjBwMy8NMrlqTC2pZ2RkxxaB4fc4R2G05PCxxOlAh9gP7pMxKHsAe1wihvj\n" +
            "Q16leDQ23CQelriI6f8I8ZS+fyEA73HWjVKCigIVqpbuBM6FGR4KjYwuHVmTWT5a\n" +
            "pyrDJqT+enkj6heQ03dF9xZuU1Y1Np0GH7lVAJI7bahsRgNMfxVb6nJeTkaHOicN\n" +
            "JT4lvH25V0K5JM0DBwQmj1ccLFgVEb0wfaQBYnbJBKPLPgLg1a+GzHpo+qYkRQED\n" +
            "paCdSxg7rcKB+ef4f3h9pas0WB66hkfF0MrveeEHiNLa4jLIlvx1/s2E7Fqs4zm0\n" +
            "iisckKbsjy6HIjQg8PQKLeqcWcxN6Vd37z35Vr+juUw8Iw1F40JkLWvIlYqJsCVM\n" +
            "/EKcRO4aJiv2nXzPPyD/+nTsnAfRs9cW+nwtciBMS3BFjcwd0ssINmHi/yglOkRU\n" +
            "2Mb6V7+LbKVMyDPoY4AZV+md5bq1Zox9F4FcuN+65ZhprsY10taI29j1klZghyiX\n" +
            "VN2+x7vmX8gG5YnExwg8LnCiBPf1rxy7FdUIehFwVWnp1/fboyfkXbPLoFTOxhqK\n" +
            "t8kRbhJ0SmJXtG5fvmIb74+FNR5uJQIDAQABAoICACEkghs7pnT6WK/Y3rj7udzC\n" +
            "Ztaco+s97NUUY9uDWddPHC4XyQxHV6c65qwEXg0cb/xpRToVkEDa5E9QBvC1MJqI\n" +
            "zAHSNtfYdJGN52femMyw58BQj97HrJjm72fKntrL+O2MtFBV9FYx61sGoz53AGMP\n" +
            "YkpA6X5pFlSEHSeybrrdTIaytK4SoNdF5YN2R6pxXg2lbBhpW8x30BW4Tp3tFt9I\n" +
            "IPbMPVngNmHTSiufPDGp08Pi9YoJ5T1aslZqqnMXrz3qPo0l4dSxJY1eMPHVEn64\n" +
            "6Jueulyd6SgIwC2m5lk669WH7m31RMVQ2SJPzXKb08QUgLbWu/5ujAUcwWUitoLM\n" +
            "VKKYUPo6xEcoRSbJjC6AdYdBJG7+XK2ZnEj+YN0UCQgruBxnu3mxA2lOgpIoNwmW\n" +
            "jeOapOqjEEvCBLlXCrY1Rgk8D2xVQVHKDW95Kbr8vOIP4mLFKc1mE6/gJJJKRHG7\n" +
            "GE8Tfi/1b71h5+EYcrib3vHmZ0Y65zCRzg3vdnfEgSHXnQ1BBlHOMns6cbcW/KsR\n" +
            "5ex610hZKQ0RZHv3iTH+uG7NEdcfbn4KT4fgPeDouGTNmdWnTQysgTrPtwvabwl2\n" +
            "kOnLsERknKv3cbVZe/O3EUU2+aPDlOkWPpAXchMjAFfVBJRxZS8Mp9gcWMgYU1/S\n" +
            "auGaqyUaJ2gx1btrV5MtAoIBAQDz0uqLCx5V31Sq/JamYKkYmyQdxwyGO7zRKMxJ\n" +
            "lInBLFQ3/GdZtxgtVH5bhPGTf3sx6Ari6Vac3rNlmY+a6d5t/OhRZ5Yjve5WZy4j\n" +
            "8NRBtoQEoM1nb5kdI4Vf8wzt5EwtgnwoORhrWNhQWgY8cSBr4ouqe3DO45sBzEr6\n" +
            "ZvoyVl19P8hOBOi0JecFWHI+vV4Cu8UEXN9F+q1+49xvu7YRGBmeaJ3EdRRWY2Ba\n" +
            "i/PKYTVO48PmQzl92YvCaPDUrcjdpH1VLMLhBOaVMCdFz3ffwd8HH23RjUdtawiQ\n" +
            "QL8FSBu7LNVLMcgQMEu/VDF0ILbK2rWRNWk3dGnd6gI0OtAnAoIBAQDBpKor8WHP\n" +
            "B5ve35a+stQuOPlJNHHV2dp7R9W3jav1ZRfTjHz7LRp+LjLQjRGPmAvWn8znF7kt\n" +
            "nB0xjtzinpaFt5Xlg0FtLuY+XwhrNMlPHxW5nmI/EdHRkKNg0+46C+nC+FZmdKge\n" +
            "iv166GHmNi1/LXqneEzGEqRw6AAbhuRkxG9oXBorKIWzvTt75Z5c9uqPK4nKq6/2\n" +
            "CZ9a/9tqMHAsg5fkdwQunZ1Cl7Kq2yHe3kjKcE74mLT4Agt8gTRBo0dce039nRC/\n" +
            "Z/y2XXtDo+7jDJln1d4w8k9QCbKnSl6Fwc/HUchm+DGwTyJAXaZexfrYZfY6KSo7\n" +
            "yxqeuRz9IPLTAoIBAELb/ErCcsATbJEykFDxQo0nxaCQu4N3kBA+wr4OiDO7ZyCv\n" +
            "CtwUFAIiIEASVXgfxfVqbsnIOGLtwn3CvRk6ti6s4juoTwhgBaIVyxY57MpUNSFb\n" +
            "wtdxR6gw3FryOAJnzufP1yHkfii5hR+LgBhE4SIUTj4ieU2SJig5D/pYEJWLDznQ\n" +
            "KqoSOIllt1LE7Rb9MHFJcf3/RY5X7glUjFbrP0KyvBuvDkwX2srRl1Nc+y3suAR4\n" +
            "+ngCyRoUdHmL8zYurVzxxRh9HLc9c8lHGg3PlUx7SiAW4zNKLWL4rbF4v6jAHDKY\n" +
            "Waiz7utZgvvlJ2Zzrgw0KwO1hLObLYiXqaEO25MCggEARJf+y8ganIqWdVGAhZa1\n" +
            "QJZf/8V7GYStNwRQOVAO/MNNuFtInnTwsIfO6j8+XD5A20sJ+0ZVC0oEsVxo9EG3\n" +
            "EjDhAUcIcxOs7aUeR1OQh918BZ+ML8kxKOnyZcdM2PUFXlI30N07/G3BsrsEitbg\n" +
            "MO+8RfyAPZ228GPOPfH/j45h5HbTsjwNeIjsvEuvWEEC/6otikRc9WudLk/5uljk\n" +
            "M3B96qkaKyUb4s33tTwgeGEHKea0oqz9VejrhrQB/cpH6gOnsdHkb9j3FOZyVpiI\n" +
            "c5cv9C83IOGEpNZ6mXXnm7v2zSU0Nv1uJIUznddOqr24yNT9bsbMNhgRGu55zVTR\n" +
            "jwKCAQBogcoj3Wl5UWDHNlDnUY41yoQ/lCfi3wmGytjQ3AqSzjxWTLtVuBAJrmgQ\n" +
            "X8Twpd+VjIVuWBSProp7n7HZ3UgS2c4IOkpYYxXU9cDDbTJXr0M1F12jTp9RM1d0\n" +
            "Xfet8GPC519wKVKqsyK6aYISbxZ29WqLC4Hz9XaAvlhDcUzcw/QvLcD5MlXOvQck\n" +
            "0m8tT0R0+wwLRq3UbL5Ax7svkjr2HmQctbK2r7D10Vogh6YQEspgzmJ4V3zeKYdr\n" +
            "CdHvMVEV7TMS7XuTMHytzAf90mRfjcks4NCRnfdp3z9gWEcFtmS4H+lWLNqyfucD\n" +
            "fRzM9DPAhHJfnZ1OTolfYHOyTR7e\n" +
            "-----END PRIVATE KEY-----";

}
