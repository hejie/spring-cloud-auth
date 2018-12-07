package com.yh.hr.repository;

import com.yh.hr.domain.FileUpload;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileRepository extends CrudRepository<FileUpload, String> {

    @Query(value = "SELECT * FROM js_sys_file_upload f WHERE f.status =0 AND f.biz_key=:bizKey ORDER BY create_date", nativeQuery = true)
    List<FileUpload> findByBizKey(@Param("bizKey") String bizKey);
}
