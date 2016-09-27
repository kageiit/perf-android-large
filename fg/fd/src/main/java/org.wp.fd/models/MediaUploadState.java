package org.wp.fd.models;

public enum MediaUploadState {
    QUEUED,
    UPLOADING,
    DELETE,
    DELETED,
    FAILED,
    UPLOADED;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
