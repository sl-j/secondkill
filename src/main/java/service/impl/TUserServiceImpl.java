package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TUser;
import mapper.TUserMapper;
import org.springframework.stereotype.Service;
import service.TUserService;

/**
 * (TUser)表服务实现类
 *
 * @author makejava
 * @since 2022-07-28 14:43:15
 */
@Service("tUserService")
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements TUserService {

}
