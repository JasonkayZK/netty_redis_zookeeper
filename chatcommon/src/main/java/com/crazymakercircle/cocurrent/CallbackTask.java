/**
 * Created by 尼恩 at 疯狂创客圈
 */

package com.crazymakercircle.cocurrent;


public interface CallbackTask<R>
{

     R execute() throws Exception;

     void onSuccess(R r);

     void onFailure(Throwable t);
}
