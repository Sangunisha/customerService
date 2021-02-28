package com.inteview.banking.customerService.base.service;

import com.inteview.banking.customerService.base.domain.BaseDomainObject;
import com.inteview.banking.customerService.base.dto.BaseDTO;
import com.inteview.banking.customerService.base.exceptions.AppException;
import com.inteview.banking.customerService.constants.StatusEnum;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Generic base class to manage CRUD operations.
 *
 * @param <T>
 * @param <U>
 */
public abstract class AbstractService<T extends BaseDomainObject, U extends BaseDTO> {

    public abstract CrudRepository<T, String> getRepository();

    public abstract U getDTO(T entity, U dto) throws AppException;

    public abstract T populateEntity(U dto, T entity) throws AppException;

    public abstract T updateModifiedParams(U dto, T existingEntity) throws AppException;

    public abstract U getNewDTO() throws AppException;

    public abstract T getNewEntity() throws AppException;

    /**
     * Common method to get the entity from the primary key and return as dto
     *
     * @param id
     * @return
     * @throws AppException
     */
    public U get(String id) throws AppException {
        return getDTO(getEntity(id), getNewDTO());
    }

    /**
     * Common method to get the entity from the primary key
     *
     * @param id
     * @return
     * @throws AppException
     */
    public T getEntity(String id) throws AppException {
        if (id == null)
            throw new AppException(AppException.AppExceptionErrorCode.parameter_missing, "id is null");
        Optional<T> entity = getRepository().findById(id);
        if (!entity.isPresent())
            throw new AppException(AppException.AppExceptionErrorCode.object_does_not_exist, "Cannot find object with id:" + id);
        return entity.get();
    }

    /**
     * Common method to create an entry in the database
     *
     * @param dto
     * @return
     * @throws AppException
     */
    public U create(U dto) throws AppException {
        if (dto == null)
            throw new AppException(AppException.AppExceptionErrorCode.parameter_missing, "dto is null");
        T entity = populateEntity(dto, getNewEntity());
        return getDTO(createEntity(entity), getNewDTO());
    }

    /**
     * Sets the base fields before creating the entity
     *
     * @param entity
     * @return
     * @throws AppException
     */
    public T setBaseDomainObjectParamsForCreateEntity(T entity) throws AppException {
        entity.setId(null);
        entity.setCreatedTs(System.currentTimeMillis());
        entity.setModifiedTs(System.currentTimeMillis());
        entity.setStatus(StatusEnum.ACTIVE);
        return entity;
    }

    /**
     * Method to create the entity in database from dto
     *
     * @param entity
     * @return
     * @throws AppException
     */
    public T createEntity(T entity) throws AppException {
        entity = setBaseDomainObjectParamsForCreateEntity(entity);
        return getRepository().save(entity);
    }

    /**
     * Method to update the entity in database from dto
     *
     * @param dto
     * @return
     * @throws AppException
     */
    public U update(U dto) throws AppException {
        if (dto == null)
            throw new AppException(AppException.AppExceptionErrorCode.parameter_missing, "dto is null");
        String id = dto.getId();
        T existingEntity = getEntity(id);

        if (existingEntity.getStatus() == StatusEnum.INACTIVE)
            throw new AppException(AppException.AppExceptionErrorCode.invalid_state, "Attempting to update an inactive object. id:" + id);
        existingEntity = updateModifiedParams(dto, existingEntity);
        return getDTO(updateEntity(existingEntity), getNewDTO());
    }

    /**
     * Method to update the entity in database from entity object itself
     *
     * @param entity
     * @return
     * @throws AppException
     */
    public T updateEntity(T entity) throws AppException {
        entity.setModifiedTs(System.currentTimeMillis());
        return getRepository().save(entity);
    }

    /**
     * Method to soft delete the entity from primary key
     *
     * @param id
     * @return
     * @throws AppException
     */
    public Boolean disable(String id) throws AppException {
        T existingEntity = getEntity(id);
        disableEntity(existingEntity);
        return true;
    }

    /**
     * Method to soft delete the entity
     *
     * @param entity
     * @return
     * @throws AppException
     */
    public T disableEntity(T entity) throws AppException {
        if (entity.getStatus() == StatusEnum.INACTIVE)
            throw new AppException(AppException.AppExceptionErrorCode.invalid_state, "Attempting to disable an inactive object. id:" + entity.getId());
        entity.setModifiedTs(System.currentTimeMillis());
        entity.setStatus(StatusEnum.INACTIVE);
        return getRepository().save(entity);
    }

    /**
     * Method to enable (set status as ACTIVE) the entity object/table row from primary key
     *
     * @param id
     * @return
     * @throws AppException
     */
    public Boolean enable(String id) throws AppException {
        T existingEntity = getEntity(id);
        enableEntity(existingEntity);
        return true;
    }

    /**
     * Method to enable (set status as ACTIVE) the entity object/table row
     *
     * @param entity
     * @return
     * @throws AppException
     */
    public T enableEntity(T entity) throws AppException {
        if (entity.getStatus() == StatusEnum.ACTIVE)
            throw new AppException(AppException.AppExceptionErrorCode.invalid_state, "Attempting to enable an active object. id:" + entity.getId());

        entity.setModifiedTs(System.currentTimeMillis());
        entity.setStatus(StatusEnum.ACTIVE);
        return getRepository().save(entity);
    }

    /**
     * Method to delete the entity
     *
     * @param id
     * @return
     * @throws AppException
     */
    public Boolean delete(String id) throws AppException {
        T existingEntity = getEntity(id);
        getRepository().delete(existingEntity);
        return true;
    }

    /**
     * Method to copy the base dto fields from the entity
     *
     * @param t
     * @param u
     * @return
     * @throws AppException
     */
    public U copyDTO(T t, U u) throws AppException {
        if (t != null && u != null) {
            u.setCreatedTs(t.getCreatedTs());
            u.setId(t.getId());
            u.setModifiedTs(t.getModifiedTs());
            u.setVersion(t.getVersion());
            u.setStatus(t.getStatus());
            return u;
        }
        throw new AppException(AppException.AppExceptionErrorCode.parameter_missing, "entity=" + t + " dto=" + u);
    }
}
