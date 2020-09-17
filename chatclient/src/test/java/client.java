import com.crazymakercircle.im.common.bean.User;
import com.crazymakercircle.imClient.client.ClientSession;
import com.crazymakercircle.imClient.sender.ChatSender;
import org.junit.Test;

/**
 * Created by 尼恩 at 疯狂创客圈
 */

public class client {

    @Test
    public void sendChatMsg() {
        ChatSender sender = new ChatSender();
        User user = new User();
        user.setUid("1");
        user.setNickName("张三");
        user.setSessionId("-1");
        sender.setSession(new ClientSession(null));
        sender.setUser(user);
        sender.sendChatMsg("dd", "1");

        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
