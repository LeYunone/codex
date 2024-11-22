package com.leyunone.codex.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.List;

/**
 *基础Geteway
 * @author LeYunone
 */
public interface BaseDao<DO> extends IService<DO> {

    boolean insertOrUpdate(Object entity);

    /**
     * 批量插入
     * @param params  会通过copy转换为实体
     * @return
     */
    boolean insertOrUpdateBatch(List params);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    boolean deleteById(Serializable id);

    /**
     * 根据ids批量删除
     * @param ids
     * @return
     */
    boolean deleteByIdBatch(List ids);

    <R> boolean deleteLogicById(Serializable id, SFunction<DO, R> tableId);


    /**
     * 万能eq分页查询
     * @return
     */
    Page<DO> selectByConPage(Object o, Page page);

    Page<DO> selectByConPage(Object o, Integer index, Integer size);

    <R,Z> List<R> selectByConOrder(Object o, Class<R> clazz, int type, SFunction<DO, Z>... tables);

    /**
     * 万能eq分页查询 根据condition和isDesc排序查询
     * @param con
     * @param isDesc
     * @return
     */
    <R>Page<DO> selectByConOrderPage(Object con, Page page, SFunction<DO, R> function, boolean isDesc);

    List<DO> selectByCon(Object o);

    List<DO> selectByCon(LambdaQueryWrapper<DO> queryWrapper);

    List<DO> selectByCon(Object o, LambdaQueryWrapper<DO> queryWrapper);

    <R> List<R> selectByCon(Object o, Class<R> clazz);

    <R> List<R> selectByCon(Object o, Class<R> clazz, LambdaQueryWrapper<DO> queryWrapper);

    <R> R selectById(Serializable id, Class<R> clazz);

    DO selectById(Serializable id);

    List<DO> selectByIds(List<? extends Serializable> ids);

    <R> R selectOne(Object o, Class<R> clazz);

    DO selectOne(Object o);
}
