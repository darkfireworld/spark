package org.dfw.spark.core.dto;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/4/3.
 */
public class DtoInternalException extends RuntimeException implements Serializable {
    static final long serialVersionUID = 1L;
    DtoInternalCode dtoInternalCode;

    public DtoInternalException(DtoInternalCode dtoInternalCode) {
        super(dtoInternalCode.getMsg());
        this.dtoInternalCode = dtoInternalCode;
    }

    public DtoInternalCode getDtoInternalCode() {
        return this.dtoInternalCode;
    }

}
