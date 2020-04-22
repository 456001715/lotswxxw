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
public class Ar {

    private int id;
    private String name;
    private List<String> tns;
    private List<String> alias;
    public void setId(int id) {
         this.id = id;
     }
     public int getId() {
         return id;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setTns(List<String> tns) {
         this.tns = tns;
     }
     public List<String> getTns() {
         return tns;
     }

    public void setAlias(List<String> alias) {
         this.alias = alias;
     }
     public List<String> getAlias() {
         return alias;
     }

}