package com.core.enums;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/28
 */
public enum ResourceType {
    //图片处理
    IMAGE("IMAGE","imageUploadHandler"),
    //视频处理
    VIDEO("VIDEO","videoUploadHandler"),
    //pdf处理
    PDF("PDF","pdfUploadHandler");

    /** 资源类型 */
    private final String typeName;
    /** 资源上传Handler，实现类beanName */
    private final String uploadHandler;

    ResourceType(String typeName, String uploadHandler) {
        this.typeName = typeName;
        this.uploadHandler = uploadHandler;
    }

    /**
     * 根据key获取枚举对象
     */
    public static ResourceType convert(String name) {
        for (ResourceType e : ResourceType.values()) {
            if (e.getTypeName().equalsIgnoreCase(name)) {
                return e;
            }
        }
        return null;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getUploadHandler() {
        return uploadHandler;
    }
}
