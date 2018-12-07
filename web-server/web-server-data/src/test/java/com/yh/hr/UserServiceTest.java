package com.yh.hr;

import com.yh.hr.domain.Employee;
import com.yh.hr.domain.Points;
import com.yh.hr.domain.User;
import com.yh.hr.domain.WxUser;
import com.yh.hr.dto.HttpResponse;
import com.yh.hr.dto.TokenReq;
import com.yh.hr.dto.UserReq;
import com.yh.hr.repository.WxUserRepository;
import com.yh.hr.service.EmployeeService;
import com.yh.hr.service.PointsService;
import com.yh.hr.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class UserServiceTest {

    private static final Logger log = LoggerFactory.getLogger(UserServiceTest.class);


    @Autowired
    private UserService userService;
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PointsService pointsService;


    @Autowired
    private WxUserRepository wxUserRepository;

    @Test
    public void findByWxOpenid() {
        User user = userService.findByUserCode("00015664");

        Employee emp = employeeService.findByEmpCode(user.getUserCode());

        Points points = pointsService.findByUserCode(user.getUserCode());

        log.info("用户= {} , 员工={} ,积分={}", user, emp, points);
    }

    @Test
    public void update() {
        UserReq user = new UserReq();
        user.setOpenId("wx934b3d449f3df95b");
        user.setUserCode("00015664");
        HttpResponse<TokenReq> response = userService.update(user);
        log.info("{}", response);
    }

    @Test
    public void save() {
        WxUser wxUser = new WxUser();
        wxUser.setSubscribe(true);
        wxUserRepository.save(wxUser);
    }
}