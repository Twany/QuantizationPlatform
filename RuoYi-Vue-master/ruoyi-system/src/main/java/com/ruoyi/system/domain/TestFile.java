package com.ruoyi.system.domain;

/**
 * @Author：渐悟
 * @Date：2023/11/23
 * @Github: https://github.com/Twany
 * @Description：
 */
public class TestFile {
    private int id;

    private int parentId;

    @Override
    public String toString() {
        return "TestFile{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", desc='" + desc + '\'' +
                ", auth='" + auth + '\'' +
                ", editTime=" + editTime +
                ", createTime=" + createTime +
                ", fileSize='" + fileSize + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                '}';
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String fileName;
    private String fileType;
    private String desc;
    private String auth;

    private Long editTime;
    private Long createTime;

    private String fileSize;

    private String fileUrl;

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public Long getEditTime() {
        return editTime;
    }

    public void setEditTime(Long editTime) {
        this.editTime = editTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
