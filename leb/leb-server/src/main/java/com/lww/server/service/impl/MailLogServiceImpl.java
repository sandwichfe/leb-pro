package com.lww.server.service.impl;

import com.lww.server.pojo.MailLog;
import com.lww.server.mapper.MailLogMapper;
import com.lww.server.service.IMailLogService;
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
public class MailLogServiceImpl extends ServiceImpl<MailLogMapper, MailLog> implements IMailLogService {

}
