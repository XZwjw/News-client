package com.example.wangjiawang.complete.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Description:
 * Created by wangjiawang on 2018/2/5.
 * complete
 */

/**
 *   {
 "title": "继《摔跤吧爸爸》后印度又出神作：离婚吧妈妈！",
 "docurl": "http://lady.163.com/18/0130/21/D9E8ALP100267VA9.html",
 "commenturl": "http://comment.lady.163.com/lady_bbs/D9E8ALP100267VA9.html",
 "tienum": 1914,
 "tlastid": "<a href='http://lady.163.com/' class='link'>女人</a>",
 "tlink": "http://lady.163.com/",
 "label": "女人",
 "keywords": [
 {
 "akey_link": "http://lady.163.com/keywords/7/b/79bb5a5a/1.html",
 "keyname": "离婚"
 },
 {
 "akey_link": "http://lady.163.com/keywords/5/b/5bb666b4/1.html",
 "keyname": "家暴"
 },
 {
 "akey_link": "http://lady.163.com/keywords/5/0/5c0f5c39/1.html",
 "keyname": "小尹"
 }
 ],
 "time": "01/30/2018 21:26:15",
 "newstype": "article",
 "imgurl": "bigimg||http://cms-bucket.nosdn.127.net/bd3404bb6b954796bd629b9514db3d0e20180130212613.png||||||||",
 "add1": "http://cms-bucket.nosdn.127.net/bd3404bb6b954796bd629b9514db3d0e20180130212613.png?imageView&thumbnail=600y300",
 "add2": "",
 "add3": ""
 }
 */
public class News implements Parcelable {
    String id;
    String title;
    String docurl;
    String commenturl;
    int tienum;
    String tlastid;
    String tlink;
    String label;
    List<Keywords> keywords;
    String time;
    String newstype;
    String imgurl;
    String add1;
    String add2;
    String add3;
    public static final String TYPE_ARTICLE = "article";
    public static final String TYPE_PHOTOSET = "photoset";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setDocurl(String docurl) {
        this.docurl = docurl;
    }
    public String getDocurl() {
        return docurl;
    }

    public void setCommenturl(String commenturl) {
        this.commenturl = commenturl;
    }
    public String getCommenturl() {
        return commenturl;
    }

    public void setTienum(int tienum) {
        this.tienum = tienum;
    }
    public int getTienum() {
        return tienum;
    }

    public void setTlastid(String tlastid) {
        this.tlastid = tlastid;
    }
    public String getTlastid() {
        return tlastid;
    }

    public void setTlink(String tlink) {
        this.tlink = tlink;
    }
    public String getTlink() {
        return tlink;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }

    public void setKeywords(List<Keywords> keywords) {
        this.keywords = keywords;
    }
    public List<Keywords> getKeywords() {
        return keywords;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setNewstype(String newstype) {
        this.newstype = newstype;
    }
    public String getNewstype() {
        return newstype;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
    public String getImgurl() {
        return imgurl;
    }

    public void setAdd1(String add1) {
        this.add1 = add1;
    }
    public String getAdd1() {
        return add1;
    }

    public void setAdd2(String add2) {
        this.add2 = add2;
    }
    public String getAdd2() {
        return add2;
    }

    public void setAdd3(String add3) {
        this.add3 = add3;
    }
    public String getAdd3() {
        return add3;
    }
    public static class Keywords implements Parcelable {

        private String akey_link;
        private String keyname;
        public void setAkey_link(String akey_link) {
            this.akey_link = akey_link;
        }
        public String getAkey_link() {
            return akey_link;
        }

        public void setKeyname(String keyname) {
            this.keyname = keyname;
        }
        public String getKeyname() {
            return keyname;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.akey_link);
            dest.writeString(this.keyname);
        }

        public Keywords() {
        }

        protected Keywords(Parcel in) {
            this.akey_link = in.readString();
            this.keyname = in.readString();
        }

        public static final Creator<Keywords> CREATOR = new Creator<Keywords>() {
            @Override
            public Keywords createFromParcel(Parcel source) {
                return new Keywords(source);
            }

            @Override
            public Keywords[] newArray(int size) {
                return new Keywords[size];
            }
        };
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.docurl);
        dest.writeString(this.commenturl);
        dest.writeInt(this.tienum);
        dest.writeString(this.tlastid);
        dest.writeString(this.tlink);
        dest.writeString(this.label);
        dest.writeTypedList(this.keywords);
        dest.writeString(this.time);
        dest.writeString(this.newstype);
        dest.writeString(this.imgurl);
        dest.writeString(this.add1);
        dest.writeString(this.add2);
        dest.writeString(this.add3);
    }

    public News() {
    }

    protected News(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.docurl = in.readString();
        this.commenturl = in.readString();
        this.tienum = in.readInt();
        this.tlastid = in.readString();
        this.tlink = in.readString();
        this.label = in.readString();
        this.keywords = in.createTypedArrayList(Keywords.CREATOR);
        this.time = in.readString();
        this.newstype = in.readString();
        this.imgurl = in.readString();
        this.add1 = in.readString();
        this.add2 = in.readString();
        this.add3 = in.readString();
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel source) {
            return new News(source);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    /**
     * 获取article部分url
     * @return
     */
    public String getArticleId() {
        String[] strings = docurl.substring(0,docurl.length()-5).split("/");
        return strings[strings.length-1];
    }

    /**
     * 获取atlas部分url
     * docurl:http://news.163.com/photoview/00AP0001/2293870.html#p=DJ0US2RC00AP0001NOS中的0001/2293870.
     * @return
     */
    public String getAtlasId() {
        String[] strings = docurl.split(".html")[0].split("/");
        int length = strings.length;
        int path1Length = strings[length-2].length();
        String string = strings[length-2].substring(path1Length-4,path1Length)+"/"+strings[length-1];
        return string;
    }
}
