package edu.fdu.ivos.user.controller;

import edu.fdu.ivos.base.response.JsonResult;
import edu.fdu.ivos.user.pojo.dto.UserLoginParam;
import edu.fdu.ivos.user.pojo.dto.UserQuery;
import edu.fdu.ivos.user.pojo.dto.UserSaveParam;
import edu.fdu.ivos.user.pojo.vo.UserVO;
import edu.fdu.ivos.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("login")
    public JsonResult login(@RequestBody UserLoginParam userLoginParam){
        log.debug("User login, userLoginParam={}", userLoginParam);
        // Ctrl + click method name to go to interface, Ctrl + Alt to go to implementation
        UserVO userVO = userService.login(userLoginParam);
        return JsonResult.ok(userVO);
    }

    @PostMapping("save")
    // The data from the frontend has already been converted to query string format by qs (QueryString)
    // So there's no need to use @RequestBody for mapping
    // public JsonResult saveUser(@RequestBody UserSaveParam userSaveParam){
    public JsonResult saveUser(UserSaveParam userSaveParam){
        log.debug("Saving user, userSaveParam={}", userSaveParam);
        userService.saveUser(userSaveParam);
        return JsonResult.ok();
    }

    @GetMapping("select")
    public JsonResult selectUser(UserQuery userQuery){
        List<UserVO> list = userService.selectUser(userQuery);
        return JsonResult.ok(list);
    }

    @PostMapping("/update/password/{userId}")
    public JsonResult resetPassword(@PathVariable Long userId){
        log.debug("Resetting password, userId={}", userId);
        userService.resetPassword(userId);
        return JsonResult.ok();
    }

    @PostMapping("/update/status/{userId}/{status}")
    public JsonResult updateStatus(
            @PathVariable Long userId, @PathVariable String status){
        log.debug("Updating user status, userId={}, status={}", userId, status);
        userService.updateStatus(userId, status);
        return JsonResult.ok();
    }

    @PostMapping("/delete/{userId}")
    public JsonResult deleteUser(@PathVariable Long userId){
        log.debug("Deleting user, userId={}", userId);
        userService.deleteUser(userId);
        return JsonResult.ok();
    }

    @GetMapping("/select/audit/{parentId}")
    public JsonResult selectAuditList(@PathVariable Long parentId){
        log.debug("Fetching audit approver list, parentId={}", parentId);
        List<UserVO> list = userService.selectAuditList(parentId);
        return JsonResult.ok(list);
    }
}
