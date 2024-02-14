package org.kaanalkim.authserver.payload.dto;

public interface HasPermissionDTO {
    Long getId();

    String getController();

    String getControllerAlias();

    String getPermissionName();

    String getDescription();

    String getHasPermission();
}