package com.leyunone.codex.model.data;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/1/9 16:10
 */
@HeadFontStyle(bold = true)
public class CodeInfoData {

    @ExcelProperty(value = "姓名",index = 0)
    @ContentStyle(wrapped = true,horizontalAlignment = HorizontalAlignment.CENTER)
    @ColumnWidth(15)
    private String realUserName;
    @ExcelProperty(value = "部门",index = 1)
    @ColumnWidth(15)
    @ContentStyle(wrapped = true,horizontalAlignment = HorizontalAlignment.CENTER)
    private String groupName;
    @ExcelProperty(value = "删除总行数",index = 2)
    @ColumnWidth(18)
    @ContentStyle(wrapped = true,horizontalAlignment = HorizontalAlignment.CENTER)
    private String deletions;
    @ExcelProperty(value = "新增总行数",index = 3)
    @ColumnWidth(18)
    @ContentStyle(wrapped = true,horizontalAlignment = HorizontalAlignment.CENTER)
    private String additions;
    @ExcelProperty(value = "代码总行数",index = 4)
    @ColumnWidth(18)
    @ContentStyle(wrapped = true,horizontalAlignment = HorizontalAlignment.CENTER)
    private String total;
    @ExcelProperty(value = "项目总数",index = 5)
    @ColumnWidth(18)
    @ContentStyle(wrapped = true,horizontalAlignment = HorizontalAlignment.CENTER)
    private String projectNumber;

    public String getRealUserName() {
        return realUserName;
    }

    public CodeInfoData setRealUserName(String realUserName) {
        this.realUserName = realUserName;
        return this;
    }

    public String getGroupName() {
        return groupName;
    }

    public CodeInfoData setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public String getDeletions() {
        return deletions;
    }

    public CodeInfoData setDeletions(String deletions) {
        this.deletions = deletions;
        return this;
    }

    public String getAdditions() {
        return additions;
    }

    public CodeInfoData setAdditions(String additions) {
        this.additions = additions;
        return this;
    }

    public String getTotal() {
        return total;
    }

    public CodeInfoData setTotal(String total) {
        this.total = total;
        return this;
    }

    public String getProjectNumber() {
        return projectNumber;
    }

    public CodeInfoData setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
        return this;
    }
}
