package edu.fdu.ivos.user.service.impl;

import edu.fdu.ivos.base.exception.ServiceException;
import edu.fdu.ivos.base.response.StatusCode;
import edu.fdu.ivos.user.mapper.UserMapper;
import edu.fdu.ivos.user.pojo.dto.UserLoginParam;
import edu.fdu.ivos.user.pojo.dto.UserQuery;
import edu.fdu.ivos.user.pojo.dto.UserSaveParam;
import edu.fdu.ivos.user.pojo.entity.User;
import edu.fdu.ivos.user.pojo.vo.UserVO;
import edu.fdu.ivos.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
/* @Service annotation marks this class as a service layer implementation */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public UserVO login(UserLoginParam userLoginParam) {
        log.debug("Login logic: userLoginParam={}", userLoginParam);
        UserVO userVO = userMapper.selectByUsername(userLoginParam.getUsername());
        if (userVO == null) {
            throw new ServiceException(StatusCode.USERNAME_ERROR);
        }
        if (!userVO.getPassword().equals(userLoginParam.getPassword())) {
            throw new ServiceException(StatusCode.PASSWORD_ERROR);
        }
        return userVO;
    }

    @Override
    public void saveUser(UserSaveParam userSaveParam) {
        User user = new User();
        BeanUtils.copyProperties(userSaveParam, user);
        // If the ID is null, it is a new user
        if (user.getId() == null) {
            // Note: employee accounts are not self-registered; a default password is set by the system
            user.setPassword("123456");
            user.setCreateTime(new Date());
            userMapper.insert(user);
        } else { // If the ID is present, it's an update
            user.setUpdateTime(new Date());
            userMapper.update(user);
        }
    }

    @Override
    public List<UserVO> selectUser(UserQuery userQuery) {
        List<UserVO> list = userMapper.selectUser(userQuery);
        return list;
    }

    @Override
    public void resetPassword(Long userId) {
        User user = new User();
        user.setId(userId);
        user.setPassword("root");
        user.setUpdateTime(new Date());
        userMapper.update(user);
    }

    @Override
    public void updateStatus(Long userId, String status) {
        log.debug("Updating user status: userId={}, status={}", userId, status);
        User user = new User();
        user.setId(userId);
        user.setStatus(status);
        user.setUpdateTime(new Date());
        userMapper.update(user);
    }

    @Override
    public void deleteUser(Long userId) {
        log.debug("Deleting user: userId={}", userId);
        userMapper.deleteUser(userId);
    }

    @Override
    public List<UserVO> selectAuditList(Long parentId) {
        log.debug("Fetching approvers: parentId={}", parentId);
        // 1. Prepare an empty list to store approvers
        ArrayList<UserVO> userVOList = new ArrayList<>();
        // 2. Get the direct supervisor by parentId and add to the list
        UserVO auditUser1 = userMapper.selectById(parentId);
        userVOList.add(auditUser1);
        // 3. If the direct supervisor exists and has their own supervisor
        if (auditUser1 != null && auditUser1.getParentId() != null) {
            // 4. Get the supervisor's supervisor and add to the list
            UserVO auditUser2 = userMapper.selectById(auditUser1.getParentId());
            userVOList.add(auditUser2);
        }
        // 5. Return the list of approvers to the controller
        return userVOList;
    }
}
