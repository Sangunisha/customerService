package com.inteview.banking.customerService.base.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inteview.banking.customerService.constants.StatusEnum;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Created by sangeetha on 22/02/21.
 */
public class BaseDTO<T>{

    @Size(max=255)
    protected String id;

//    @JsonIgnore
    @Min(value=0)
    protected Long createdTs;

//    @JsonIgnore
    @Min(value=0)
    protected Long modifiedTs;

    @NotNull
    @Enumerated(EnumType.STRING)
    protected StatusEnum status = StatusEnum.ACTIVE;

    @JsonIgnore
    protected Long version = 0L;

    public String getId() {
        return id;
    }

    public T setId(String id) {
        this.id = id;
        return (T)this;
    }

    public Long getCreatedTs() {
        return createdTs;
    }

    public T setCreatedTs(Long createdTs) {
        this.createdTs = createdTs;
        return (T)this;
    }

    public Long getModifiedTs() {
        return modifiedTs;
    }

    public T setModifiedTs(Long modifiedTs) {
        this.modifiedTs = modifiedTs;
        return (T)this;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public T setStatus(StatusEnum status) {
        this.status = status;
        return (T)this;
    }

    public Long getVersion() {
        return version;
    }

    public T setVersion(Long version) {
        this.version = version;
        return (T)this;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof BaseDTO)) return false;
        BaseDTO<?> baseDTO = (BaseDTO<?>) o;
        if (id!=null && !id.trim().isEmpty() && baseDTO.id!=null && !baseDTO.id.trim().isEmpty()) {
//            System.out.println("Comparing id="+id + " baseDTO.id="+baseDTO.id + " equals="+Objects.equals(id, baseDTO.id));
            return Objects.equals(id, baseDTO.id);
        }
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        if (this.id!=null && !this.id.trim().isEmpty())
            return Objects.hash(id);
        return super.hashCode();
    }
}
