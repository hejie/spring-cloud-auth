package com.yh.hr.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PointsListDto implements Serializable {

    private List<UserPointsDto> userPointsList;

    private CPage page;

}
