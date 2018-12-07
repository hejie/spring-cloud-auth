package com.yh.hr.service;


import com.yh.hr.domain.*;
import com.yh.hr.dto.HttpResponse;
import com.yh.hr.dto.TokenReq;
import com.yh.hr.dto.UserDto;
import com.yh.hr.dto.UserReq;
import com.yh.hr.exception.HttpError;
import com.yh.hr.exception.HttpException;
import com.yh.hr.repository.TokenRepository;
import com.yh.hr.repository.UserRepository;
import com.yh.hr.repository.WxUserRepository;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class.getName());


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WxUserRepository wxUserRepository;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private PointsService pointsService;
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private TokenRepository tokenRepository;

    @Transactional(readOnly = true)
    public HttpResponse<UserDto> findUserByOpenId(String userCode) {
        User user = userRepository.findByUserCode(userCode);
        if (user != null) {
            HttpResponse<UserDto> response = new HttpResponse();

            UserDto userDto = new UserDto();

            userDto.setUserCode(user.getUserCode());
            userDto.setUserName(user.getUserName());
            userDto.setAvatar(user.getAvatar());
            userDto.setBirthday(user.getBirthday());
            userDto.setNickName(user.getNickName());

            Employee emp = employeeService.findByEmpCode(user.getUserCode());
            if (emp != null) {
                userDto.setOfficeName(emp.getOfficeName());
            }

            Points points = pointsService.findByUserCode(user.getUserCode());
            if (points != null) {
                userDto.setAvailablePoints(points.getAvailablePoints());
            }

            response.setData(userDto);
            return response;
        } else {
            throw new HttpException(HttpError.ERROR42);
        }

    }

    @Transactional(readOnly = true)
    public User findByOpenId(String openId) {
        return userRepository.findByWxOpenid(openId);
    }


    @Transactional(readOnly = true)
    public User findByUserCode(String code) {
        User user = userRepository.findByUserCode(code);
        if (user != null) {
            return user;
        } else {
            throw new HttpException(HttpError.ERROR40);
        }
    }


    public WxUser isSubscribedByCode(String code) throws WxErrorException {
        WxMpOAuth2AccessToken token = wxMpService.oauth2getAccessToken(code);
        return saveUser(token.getOpenId());
    }

    @Transactional(value = "transactionManagerPrimary")
    public WxUser saveUser(String openId) throws WxErrorException {
        // 查看是否已经关注
        if (isSubscribed(openId)) {
            return saveWXUser(openId);
        } else {
            WxUser user = wxUserRepository.findByOpenId(openId);
            if (user == null) {
                user = new WxUser();
            }
            user.setOpenId(openId);
            return user;
        }
    }

    public boolean isSubscribed(String openId) {
        WxMpUser wxMpUser;
        try {
            wxMpUser = wxMpService.getUserService().userInfo(openId);
        } catch (WxErrorException e) {
            logger.info("UserService: {}", e.getMessage());
            throw new HttpException(HttpError.ERROR41);
        }
        if (wxMpUser == null) {
            return false;
        }
        return Boolean.TRUE == wxMpUser.getSubscribe();
    }


    @Transactional(value = "transactionManagerPrimary")
    public WxUser saveWXUser(String openId) throws WxErrorException {
        WxUser user = wxUserRepository.findByOpenId(openId);
        if (user == null) {
            user = new WxUser();
        }
        WxMpUser wxMpUser = wxMpService.getUserService().userInfo(openId);
        user.setOpenId(wxMpUser.getOpenId());
        user.setAvatar(wxMpUser.getHeadImgUrl());
        user.setNickName(wxMpUser.getNickname());
        user.setSubscribe(wxMpUser.getSubscribe());
        wxUserRepository.save(user);
        return user;
    }


    @Transactional(value = "transactionManagerPrimary")
    public HttpResponse<TokenReq> update(UserReq u) {

        String idCard = u.getIdCard();
        if (StringUtils.isEmpty(idCard)) {
            throw new HttpException(HttpError.ERROR43);
        }

        String password = idCard;

        User user = findByUserCode(u.getUserCode());

        if (!password.equals(user.getPassword())) {
            throw new HttpException(HttpError.ERROR44);
        }


        user.setWxOpenid(u.getOpenId());
        userRepository.save(user);


        TokenReq dto = new TokenReq();
//        dto.setToken(token.getToken());
//        dto.setDate(token.getCreateDate());
        HttpResponse<TokenReq> response = new HttpResponse();
        response.setMessage(HttpError.ERROR45.msg);
        response.setData(dto);

        return response;
    }

    @Transactional(value = "transactionManagerPrimary")
    public void update(String access_token, String userCode, String openId) {
        Token token = tokenRepository.findByUserId(userCode);
        if (token == null) {
            token = new Token();
        }
        token.setUserId(userCode);
        token.setOpenId(openId);
        token.setAccess_token(access_token);
        tokenRepository.save(token);


        User user = findByUserCode(userCode);
        WxUser wxUser = wxUserRepository.findByOpenId(openId);
        if (wxUser != null) {
            user.setAvatar(wxUser.getAvatar());
            user.setNickName(wxUser.getNickName());
            user.setWxOpenid(openId);
            userRepository.save(user);
        }
    }


}