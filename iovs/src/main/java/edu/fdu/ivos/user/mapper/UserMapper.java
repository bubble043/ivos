package edu.fdu.ivos.user.mapper;

import edu.fdu.ivos.user.pojo.dto.UserQuery;
import edu.fdu.ivos.user.pojo.entity.User;
import edu.fdu.ivos.user.pojo.vo.UserVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/* This annotation is added to the data access interface to indicate that this class has CRUD permissions */
/* Create, Read, Update, Delete */
@Repository
public interface UserMapper {
    UserVO selectByUsername(String username);

    void insert(User user);

    List<UserVO> selectUser(UserQuery userQuery);

    void update(User user);

    void deleteUser(Long userId);

    UserVO selectById(Long parentId);
}
