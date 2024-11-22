package com.leyunone.codex.model.bo;

import lombok.Data;

public class StorageBO {

    private Integer storageId;

    private String storageUrl;

    private String storageName;
    
    private String token;

    public Integer getStorageId() {
        return storageId;
    }

    public StorageBO setStorageId(Integer storageId) {
        this.storageId = storageId;
        return this;
    }

    public String getStorageUrl() {
        return storageUrl;
    }

    public StorageBO setStorageUrl(String storageUrl) {
        this.storageUrl = storageUrl;
        return this;
    }

    public String getStorageName() {
        return storageName;
    }

    public StorageBO setStorageName(String storageName) {
        this.storageName = storageName;
        return this;
    }

    public String getToken() {
        return token;
    }

    public StorageBO setToken(String token) {
        this.token = token;
        return this;
    }
}
