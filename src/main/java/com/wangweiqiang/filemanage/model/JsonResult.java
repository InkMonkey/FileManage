package com.wangweiqiang.filemanage.model;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 统一返回结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonResult implements Serializable {

    private static final long serialVersionUID = -6641293143711898927L;

    //状态：0为成功，1为失败
    private int status;

    private String msg;

    private Object data;

    public JsonResult(int status, String msg){
        this.status = status;
        this.msg = msg;
    }
}
