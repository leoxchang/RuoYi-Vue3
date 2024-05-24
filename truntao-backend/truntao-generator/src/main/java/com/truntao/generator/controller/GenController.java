package com.truntao.generator.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlCreateTableStatement;
import com.github.pagehelper.Page;
import com.truntao.common.core.domain.R;
import com.truntao.common.core.page.PageDTO;
import com.truntao.common.utils.SecurityUtils;
import com.truntao.common.utils.sql.SqlUtil;
import com.truntao.generator.domain.po.GenTable;
import com.truntao.generator.domain.dto.GenTableAllDTO;
import com.truntao.generator.domain.dto.GenTableColumnDTO;
import com.truntao.generator.domain.dto.GenTableDTO;
import com.truntao.generator.domain.ro.GenTableParam;
import org.apache.commons.io.IOUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.truntao.common.annotation.Log;
import com.truntao.common.core.controller.BaseController;
import com.truntao.common.core.text.Convert;
import com.truntao.common.enums.BusinessType;
import com.truntao.generator.service.IGenTableColumnService;
import com.truntao.generator.service.IGenTableService;

/**
 * 代码生成 操作处理
 *
 * @author truntao
 */
@RestController
@RequestMapping("/tool/gen")
public class GenController extends BaseController {
    @Resource
    private IGenTableService genTableService;

    @Resource
    private IGenTableColumnService genTableColumnService;

    /**
     * 查询代码生成列表
     */
    @PreAuthorize("@ss.hasPermission('tool:gen:list')")
    @GetMapping("/list")
    public R<PageDTO<GenTableDTO>> genList(GenTableParam genTableParam) {
        startPage();
        Page<GenTableDTO> page = genTableService.selectGenTableList(genTableParam);
        return R.ok(new PageDTO<>(page));
    }

    /**
     * 修改代码生成业务
     */
    @PreAuthorize("@ss.hasPermission('tool:gen:query')")
    @GetMapping(value = "/{tableId}")
    public R<GenTableAllDTO> getInfo(@PathVariable Long tableId) {
        GenTableDTO table = genTableService.selectGenTableById(tableId);
        List<GenTableDTO> tables = genTableService.selectGenTableAll();
        List<GenTableColumnDTO> list = genTableColumnService.selectGenTableColumnListByTableId(tableId);
        GenTableAllDTO genTableAllDTO = new GenTableAllDTO();
        genTableAllDTO.setTables(tables);
        genTableAllDTO.setInfo(table);
        genTableAllDTO.setRows(list);
        return R.ok(genTableAllDTO);
    }

    /**
     * 查询数据库列表
     */
    @PreAuthorize("@ss.hasPermission('tool:gen:list')")
    @GetMapping("/db/list")
    public R<PageDTO<GenTableDTO>> dataList(GenTableParam genTableParam) {
        startPage();
        Page<GenTableDTO> page = genTableService.selectDbTableList(genTableParam);
        return R.ok(new PageDTO<>(page));
    }

    /**
     * 查询数据表字段列表
     */
    @PreAuthorize("@ss.hasPermission('tool:gen:list')")
    @GetMapping(value = "/column/{tableId}")
    public R<PageDTO<GenTableColumnDTO>> columnList(@PathVariable("tableId") Long tableId) {
        List<GenTableColumnDTO> list = genTableColumnService.selectGenTableColumnListByTableId(tableId);
        return R.ok(new PageDTO<>(list));
    }

    /**
     * 导入表结构（保存）
     */
    @PreAuthorize("@ss.hasPermission('tool:gen:import')")
    @Log(title = "代码生成", businessType = BusinessType.IMPORT)
    @PostMapping("/importTable")
    public R<Void> importTableSave(String tables) {
        String[] tableNames = Convert.toStrArray(tables);
        // 查询表信息
        List<GenTable> tableList = genTableService.selectDbTableListByNames(tableNames);
        genTableService.importGenTable(tableList, SecurityUtils.getUsername());
        return R.ok();
    }

    /**
     * 修改保存代码生成业务
     */
    @PreAuthorize("@ss.hasPermission('tool:gen:edit')")
    @Log(title = "代码生成", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Void> editSave(@Validated @RequestBody GenTableParam genTableParam) {
        genTableService.validateEdit(genTableParam);
        genTableService.updateGenTable(genTableParam);
        return R.ok();
    }

    /**
     * 删除代码生成
     */
    @PreAuthorize("@ss.hasPermission('tool:gen:remove')")
    @Log(title = "代码生成", businessType = BusinessType.DELETE)
    @DeleteMapping("/{tableIds}")
    public R<Void> remove(@PathVariable Long[] tableIds) {
        genTableService.deleteGenTableByIds(tableIds);
        return R.ok();
    }

    /**
     * 预览代码
     */
    @PreAuthorize("@ss.hasPermission('tool:gen:preview')")
    @GetMapping("/preview/{tableId}")
    public R<Map<String, String>> preview(@PathVariable("tableId") Long tableId) {
        Map<String, String> dataMap = genTableService.previewCode(tableId);
        return R.ok(dataMap);
    }

    /**
     * 生成代码（下载方式）
     */
    @PreAuthorize("@ss.hasPermission('tool:gen:code')")
    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
    @GetMapping("/download/{tableName}")
    public void download(HttpServletResponse response, @PathVariable("tableName") String tableName) throws IOException {
        byte[] data = genTableService.downloadCode(tableName);
        genCode(response, data);
    }

    /**
     * 生成代码（自定义路径）
     */
    @PreAuthorize("@ss.hasPermission('tool:gen:code')")
    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
    @GetMapping("/genCode/{tableName}")
    public R<Void> genCode(@PathVariable("tableName") String tableName) {
        genTableService.generatorCode(tableName);
        return R.ok();
    }

    /**
     * 同步数据库
     */
    @PreAuthorize("@ss.hasPermission('tool:gen:edit')")
    @Log(title = "代码生成", businessType = BusinessType.UPDATE)
    @GetMapping("/syncDb/{tableName}")
    public R<Void> syncDb(@PathVariable("tableName") String tableName) {
        genTableService.syncDb(tableName);
        return R.ok();
    }

    /**
     * 批量生成代码
     */
    @PreAuthorize("@ss.hasPermission('tool:gen:code')")
    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
    @GetMapping("/batchGenCode")
    public void batchGenCode(HttpServletResponse response, String tables) throws IOException {
        String[] tableNames = Convert.toStrArray(tables);
        byte[] data = genTableService.downloadCode(tableNames);
        genCode(response, data);
    }

    /**
     * 生成zip文件
     */
    private void genCode(HttpServletResponse response, byte[] data) throws IOException {
        response.reset();
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment; filename=\"truntao.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }

    /**
     * 创建表结构（保存）
     */
    @PreAuthorize("@ss.hasRole('admin')")
    @Log(title = "创建表", businessType = BusinessType.OTHER)
    @PostMapping("/createTable")
    public R<Void> createTableSave(String sql) {
        try {
            SqlUtil.filterKeyword(sql);
            List<SQLStatement> sqlStatements = SQLUtils.parseStatements(sql, DbType.mysql);
            List<String> tableNames = new ArrayList<>();
            for (SQLStatement sqlStatement : sqlStatements) {
                if (sqlStatement instanceof MySqlCreateTableStatement createTableStatement && (genTableService.createTable(createTableStatement.toString()))) {
                    String tableName = createTableStatement.getTableName().replace("`", "");
                    tableNames.add(tableName);
                }
            }
            List<GenTable> tableList =
                    genTableService.selectDbTableListByNames(tableNames.toArray(new String[0]));
            String operName = SecurityUtils.getUsername();
            genTableService.importGenTable(tableList, operName);
            return R.ok();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.fail("创建表结构异常");
        }
    }
}