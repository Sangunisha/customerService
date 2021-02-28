package com.inteview.banking.customerService.base.domain;

import com.inteview.banking.customerService.constants.StatusEnum;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by sangeetha on 22/02/21.
 */
//@Entity
@MappedSuperclass
public abstract class BaseDomainObject<T extends BaseDomainObject> {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Size(max=191)
    @Column(length = 191)
    protected String id;

    @NotNull
    @Min(value = 0)
    @CreatedDate
    protected Long createdTs = System.currentTimeMillis();

    @NotNull
    @Min(value = 0)
    @LastModifiedDate
    protected Long modifiedTs = System.currentTimeMillis();

    @NotNull
    @Enumerated(EnumType.STRING)
    protected StatusEnum status = StatusEnum.ACTIVE;

    @Version
    @Column(name = "version")
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
        if (!(o instanceof BaseDomainObject)) return false;
        BaseDomainObject<?> that = (BaseDomainObject<?>) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
