package com.example.wangjiawang.complete.model.manage;

/**
 * Description:
 * Created by wangjiawang on 2018/2/12.
 * complete
 */

public class NewsCategory {
    /**
     * <item>新闻</item>
     <item>体育</item>
     <item>娱乐</item>
     <item>女人</item>

     <item>艺术</item>
     <item>财经</item>
     <item>NBA</item>
     <item>移动</item>

     <item>数码</item>
     <item>技术</item>
     <item>教育</item>
     <item>旅游</item>
     */
    public final static String CATEGORY_1 = "highLights";
    public final static String CATEGORY_2 = "sports";
    public final static String CATEGORY_3 = "entertainment";
    public final static String CATEGORY_4 = "lady";
    public final static String CATEGORY_5 = "art";
    public final static String CATEGORY_6 = "money";
    public final static String CATEGORY_7 = "nba";
    public final static String CATEGORY_8 = "mobile";
    public final static String CATEGORY_9 = "digital";
    public final static String CATEGORY_10 = "technology";
    public final static String CATEGORY_11 = "education";
    public final static String CATEGORY_12 = "travel";

    public final static String DEFAULT = CATEGORY_4;

    public static String getCategoryByPosition(int position) {
        String category;
        switch (position) {
            case 1:
                category = CATEGORY_1;
                break;
            case 2:
                category = CATEGORY_2;
                break;
            case 3:
                category = CATEGORY_3;
                break;
            case 4:
                category = CATEGORY_4;
                break;
            case 5:
                category = CATEGORY_5;
                break;
            case 6:
                category = CATEGORY_6;
                break;
            case 7:
                category = CATEGORY_7;
                break;
            case 8:
                category = CATEGORY_8;
                break;
            case 9:
                category = CATEGORY_9;
                break;
            case 10:
                category = CATEGORY_10;
                break;
            case 11:
                category = CATEGORY_11;
                break;
            case 12:
                category = CATEGORY_12;
                break;

                default:
                    category = CATEGORY_6;
        }
        return category;
    }


}
