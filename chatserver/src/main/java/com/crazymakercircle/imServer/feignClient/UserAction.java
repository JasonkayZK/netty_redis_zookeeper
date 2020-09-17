/**
 * Created by 尼恩 at 疯狂创客圈
 */

package com.crazymakercircle.imServer.feignClient;

import feign.Param;
import feign.RequestLine;

public interface UserAction {
    @RequestLine("GET /login/{username}/{password}")
    public String loginAction(
            @Param("username") String username,
            @Param("password") String password);


    @RequestLine("GET /{userid}")
    public String getById(
            @Param("userid") Integer userid);


}
