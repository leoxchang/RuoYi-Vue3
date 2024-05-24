package com.truntao.system.domain.dto;

import lombok.Data;

/**
 * @author zhangxinlei
 * @date 2024-03-15
 */
@Data
public class UploadFilesDTO {

    /**
     * url
     */
    private String urls;
    /**
     * 文件名
     */
    private String fileNames;
    /**
     * 新文件名
     */
    private String newFileNames;
    /**
     * 原始文件名
     */
    private String originalFilenames;
}
