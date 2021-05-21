package com.lww.server.service.impl;

import com.lww.server.pojo.Oplog;
import com.lww.server.mapper.OplogMapper;
import com.lww.server.service.IOplogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lww
 * @since 2021-01-27
 */
@Service
public class OplogServiceImpl extends ServiceImpl<OplogMapper, Oplog> implements IOplogService {

}
