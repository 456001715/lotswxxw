/**
 * Copyright 2020 bejson.com
 */
package com.lots.lotswxxw.domain.vo.music;

import lombok.Data;

import java.util.List;

@Data
public class JsonRootBean {

    private List<AllData> allData;
    private List<WeekData> weekData;
    private int code;


}