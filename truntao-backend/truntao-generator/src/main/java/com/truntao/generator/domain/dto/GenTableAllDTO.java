package com.truntao.generator.domain.dto;

import lombok.Data;

import java.util.List;

/**
 * @author zhangxinlei
 * @date 2023-09-12
 */
@Data
public class GenTableAllDTO {

    private GenTableDTO info;

    private List<GenTableColumnDTO> rows;

    private List<GenTableDTO> tables;
}
