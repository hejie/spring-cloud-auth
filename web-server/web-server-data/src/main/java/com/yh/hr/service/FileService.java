package com.yh.hr.service;


import com.yh.hr.domain.FileUpload;
import com.yh.hr.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    @Transactional(readOnly = true)
    public List<FileUpload> findByBizKey(String code) {
        return fileRepository.findByBizKey(code);
    }

}