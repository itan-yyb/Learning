package com.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.util.Date;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/29
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "资源")
public class MResource {
    @ApiModelProperty(value = "模块（商品资料，模特图，陈列图，3D图，长图，其他）")
    private String moduleName;

    @ApiModelProperty(value = "关联目标的主要信息（如商品名称，陈列编号）")
    private String ref;

    @ApiModelProperty(value = "展示方式（商品图片，商品长图，视频，音频，PDF等）")
    private String display;

    @ApiModelProperty(value = "文件名")
    private String filename;

    @ApiModelProperty(value = "文件前缀")
    private String prefix;

    @ApiModelProperty(value = "文件后缀（自动转小写）")
    private String suffix;

    @ApiModelProperty(value = "文件大小（KB）")
    private Long objectSize;

    @ApiModelProperty(value = "显示序号")
    private int queueNo;

    @ApiModelProperty(value = "OSS路径（相对）")
    private String pathName;

    @ApiModelProperty(value = "自定义数据")
    private String extInfo;

    private int mDim1Id;

    private int mDim2Id;

    private int mDim3Id;

    private int mDim4Id;

    private String column1;

    private String column2;

    private String column3;

    private String column4;

    private String tab1;

    private String tab2;

    private String tab3;

    private String tab4;

    private int ownerid;

    private int modifierid;

    private Date creationdate;

    private Date modifieddate;

    private String isactive;

    // putObject 功能用于处理错误
    private String error;

    //putQiNiu 功能使用
    private Integer errorType;
}
