package com.yh.hr.transform;

import com.yh.hr.domain.FileEntity;
import com.yh.hr.domain.FileUpload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Component
public class FileTransform {

    @Value("${oss.image.url}")
    private String oss_url;


    public String formItem(List<FileUpload> listFile) {
        if (listFile != null && listFile.size() > 0) {
            Map<String, List<FileUpload>> fileMap = listFile.stream().collect(groupingBy(FileUpload::getBizType));
            Optional<FileUpload> optional = fileMap.get("goods_main_image").stream().findFirst();
            if (optional != null && optional.isPresent()) {
                StringBuilder builder = new StringBuilder(oss_url);
                FileEntity entity = optional.get().getFileEntity();
                String goods_main_image = builder.append(entity.getFileId()).append(".").append(entity.getFileExtension()).toString();
                return goods_main_image;
            }
        }
        return null;
    }

    public List<String> formList(List<FileUpload> listFile) {
        if (listFile != null && listFile.size() > 0) {
            Map<String, List<FileUpload>> fileMap = listFile.stream().collect(groupingBy(FileUpload::getBizType));
            List<String> goods_multi_image_list = fileMap.get("goods_multi_image").stream().map(f -> {
                StringBuilder builder = new StringBuilder(oss_url);
                FileEntity entity = f.getFileEntity();
                return builder.append(entity.getFileId()).append(".").append(entity.getFileExtension()).toString();
            }).collect(Collectors.toList());
            return goods_multi_image_list;
        }
        return null;
    }


}