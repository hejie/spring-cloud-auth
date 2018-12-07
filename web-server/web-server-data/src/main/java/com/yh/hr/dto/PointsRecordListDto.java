package com.yh.hr.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PointsRecordListDto implements Serializable {


    private List<PointsRecordDto> pointsRecordList;

    private CPage page;
}
