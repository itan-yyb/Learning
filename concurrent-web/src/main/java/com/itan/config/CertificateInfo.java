package com.itan.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: ye.yanbin
 * @Date: 2022/4/13
 */
@Data
@ApiModel("证书信息")
public class CertificateInfo {
    @ApiModelProperty(value = "证书编号")
    private String no;

    @ApiModelProperty(value = "序列号")
    private String serialNo;

    @ApiModelProperty(value = "许可日期")
    private String licenseDate;

    @ApiModelProperty(value = "有效日期")
    private String validDate;

    @ApiModelProperty(value = "用户总数")
    private Integer totalCount;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "其他参数")
    private String params;

    @ApiModelProperty(value = "签名")
    private String signature;

    @ApiModelProperty(value = "当前环境用户数")
    private Integer userCount;

    @ApiModelProperty(value = "证书状态（VALID：有效的，NO_SERIALNO：没有序列号，MISMATCH_PORT：端口号不匹配，EXPIRED：已到期，USER_COUNT_EXCEEDED：超过用户数，FROZEN：冻结）")
    private String licenseStatus;
}
