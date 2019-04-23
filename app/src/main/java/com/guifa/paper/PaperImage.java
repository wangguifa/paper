package com.guifa.paper;

import java.io.Serializable;
import java.util.List;

public class PaperImage implements Serializable {

    /**
     * page_name : 要闻
     * page_num : 01
     * period_num : 2019-04-13
     * page_pic : https://rmrbcmsonline.peopleapp.com/upload/image/201904/201904130450041113.jpg?x-oss-process=image/resize,w_500
     * page_time : 1555270132
     * items : [{"points":"1047,139;1047,7;538,7;538,139;538,238;1047,238","title":"习近平电贺金正恩再次就任朝鲜国务委员会委员长","id":4052556,"articleid":3909557,"category_id":"115","authors":[],"news_timestamp":1555084800,"news_datetime":"2019-04-13","comment_count":0,"view_type":"paper_2","title_style":null,"description":null,"copyfrom":null,"news_link":null,"medias":null,"content":null},{},{},{},{},{},{},{}]
     */

    private String page_name;
    private String page_num;
    private String period_num;
    private String page_pic;
    private int page_time;
    private List<ItemsBean> items;

    public String getPage_name() {
        return page_name;
    }

    public void setPage_name(String page_name) {
        this.page_name = page_name;
    }

    public String getPage_num() {
        return page_num;
    }

    public void setPage_num(String page_num) {
        this.page_num = page_num;
    }

    public String getPeriod_num() {
        return period_num;
    }

    public void setPeriod_num(String period_num) {
        this.period_num = period_num;
    }

    public String getPage_pic() {
        return page_pic;
    }

    public void setPage_pic(String page_pic) {
        this.page_pic = page_pic;
    }

    public int getPage_time() {
        return page_time;
    }

    public void setPage_time(int page_time) {
        this.page_time = page_time;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * points : 1047,139;1047,7;538,7;538,139;538,238;1047,238
         * title : 习近平电贺金正恩再次就任朝鲜国务委员会委员长
         * id : 4052556
         * articleid : 3909557
         * category_id : 115
         * authors : []
         * news_timestamp : 1555084800
         * news_datetime : 2019-04-13
         * comment_count : 0
         * view_type : paper_2
         * title_style : null
         * description : null
         * copyfrom : null
         * news_link : null
         * medias : null
         * content : null
         */

        private String points;
        private String title;
        private int id;
        private int articleid;
        private String category_id;
        private int news_timestamp;
        private String news_datetime;
        private int comment_count;
        private String view_type;

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getArticleid() {
            return articleid;
        }

        public void setArticleid(int articleid) {
            this.articleid = articleid;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public int getNews_timestamp() {
            return news_timestamp;
        }

        public void setNews_timestamp(int news_timestamp) {
            this.news_timestamp = news_timestamp;
        }

        public String getNews_datetime() {
            return news_datetime;
        }

        public void setNews_datetime(String news_datetime) {
            this.news_datetime = news_datetime;
        }

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public String getView_type() {
            return view_type;
        }

        public void setView_type(String view_type) {
            this.view_type = view_type;
        }
    }
}