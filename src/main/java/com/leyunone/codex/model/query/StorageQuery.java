package com.leyunone.codex.model.query;

public class StorageQuery {

    private String projectId;

    private String userName;

    @Override
    public String toString() {
        return "StorageQuery{" +
                "projectId='" + projectId + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StorageQuery that = (StorageQuery) o;

        if (projectId != null ? !projectId.equals(that.projectId) : that.projectId != null) return false;
        return userName != null ? userName.equals(that.userName) : that.userName == null;
    }

    @Override
    public int hashCode() {
        int result = projectId != null ? projectId.hashCode() : 0;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        return result;
    }

    public String getProjectId() {
        return projectId;
    }

    public StorageQuery setProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public StorageQuery setUserName(String userName) {
        this.userName = userName;
        return this;
    }
}
