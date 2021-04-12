package com.lots.lotswxxw.dao;

import com.lots.lotswxxw.domain.bo.AuthUserRole;
import org.springframework.dao.DataAccessException;

/**
 * @author lots
 * @date 11:23 2018/4/22
 */
public interface AuthUserRoleMapper {

    /**
     * description TODO
     *
     * @param id 1
     * @return int
     * @throws DataAccessException when
     */
    int deleteByPrimaryKey(Integer id) throws DataAccessException;

    /**
     * description TODO
     *
     * @param record 1
     * @return int
     * @throws DataAccessException when
     */
    int insert(AuthUserRole record) throws DataAccessException;

    /**
     * description TODO
     *
     * @param record 1
     * @return int
     * @throws DataAccessException when
     */
    int insertSelective(AuthUserRole record) throws DataAccessException;

    /**
     * description TODO
     *
     * @param id 1
     * @return AuthUserRole
     * @throws DataAccessException when
     */
    AuthUserRole selectByPrimaryKey(Integer id) throws DataAccessException;

    /**
     * description TODO
     *
     * @param record 1
     * @return int
     * @throws DataAccessException when
     */
    int updateByPrimaryKeySelective(AuthUserRole record) throws DataAccessException;

    /**
     * description TODO
     *
     * @param record 1
     * @return int
     * @throws DataAccessException when
     */
    int updateByPrimaryKey(AuthUserRole record) throws DataAccessException;

    /**
     * description TODO
     *
     * @param record 1
     * @return int
     * @throws DataAccessException when
     */
    int deleteByUniqueKey(AuthUserRole record) throws DataAccessException;
}