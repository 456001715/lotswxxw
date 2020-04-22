/**
  * Copyright 2020 bejson.com 
  */
package com.lots.lotswxxw.domain.vo.music;
import java.util.List;

/**
 * Auto-generated: 2020-04-22 14:42:57
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Al {

    private long id;
    private String name;
    private String picUrl;
    private List<String> tns;
    private long pic;
    public void setId(long id) {
         this.id = id;
     }
     public long getId() {
         return id;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setPicUrl(String picUrl) {
         this.picUrl = picUrl;
     }
     public String getPicUrl() {
         return picUrl;
     }

    public void setTns(List<String> tns) {
         this.tns = tns;
     }
     public List<String> getTns() {
         return tns;
     }

    public void setPic(long pic) {
         this.pic = pic;
     }
     public long getPic() {
         return pic;
     }

}