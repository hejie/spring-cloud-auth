package com.yh.hr.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 图片
 */
@Data
@Entity
@Table(name = "js_sys_file_upload", indexes = {@Index(name = "fileId", columnList = "fileId")})
public class FileUpload {

    @Id
    private String id;
    //文件ID
    @ManyToOne
    @JoinColumn(name = "fileId")
    private FileEntity fileEntity;
    //保存后的值
    private String bizType;
    private String bizKey;
    //文件名
    private String fileName;
    //文件类型
    private String fileType;
    //状态
    @Column(length = 1)
    private String status;
    /**
     * 添加时间
     */
    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date createDate;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date updateDate;

}
