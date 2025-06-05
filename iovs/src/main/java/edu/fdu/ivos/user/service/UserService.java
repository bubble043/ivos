package edu.fdu.ivos.user.service;

import edu.fdu.ivos.user.pojo.dto.UserLoginParam;
import edu.fdu.ivos.user.pojo.dto.UserQuery;
import edu.fdu.ivos.user.pojo.dto.UserSaveParam;
import edu.fdu.ivos.user.pojo.vo.UserVO;

import java.util.List;

public interface UserService {
    UserVO login(UserLoginParam userLoginParam);

    void saveUser(UserSaveParam userSaveParam);

    List<UserVO> selectUser(UserQuery userQuery);

    void resetPassword(Long userId);

    void updateStatus(Long userId, String status);

    void deleteUser(Long userId);

    List<UserVO> selectAuditList(Long parentId);
}
