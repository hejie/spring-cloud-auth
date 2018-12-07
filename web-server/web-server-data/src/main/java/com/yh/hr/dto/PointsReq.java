package com.yh.hr.dto;

import com.yh.hr.enums.Time;
import lombok.Data;

import java.io.Serializable;

@Data
public class PointsReq implements Serializable {


    private int page;

    private int size;


    //任务时间
    private Time time;


}
