package com.truntao.system.domain.dto;

import lombok.Data;

/**
 * @author zhangxinlei
 * @date 2024-03-15
 */
@Data
public class UploadFileDTO {

    /**
     * url
     */
    private String url;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 新文件名
     */
    private String newFileName;
    /**
     * 原始文件名
     */
    private String originalFilename;
}
