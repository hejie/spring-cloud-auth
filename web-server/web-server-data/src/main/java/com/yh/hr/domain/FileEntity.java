package com.yh.hr.domain;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "js_sys_file_entity", indexes = {@Index(name = "fileId", columnList = "fileId")})
public class FileEntity {

    @Id
    //文件ID
    private String fileId;
    //扩展名
    private String fileExtension;
    //MD5
    private String fileMd5;
    //文件路径
    private String filePath;
    //文件类型
    private String fileContentType;
    //文件大小
    private Long fileSize;

}