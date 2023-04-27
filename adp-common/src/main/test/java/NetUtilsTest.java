import com.fyp.adp.common.utils.EmailUtil;
import com.fyp.adp.common.utils.HttpClientUtils;
import com.google.common.collect.Maps;
import org.junit.Test;

import javax.mail.MessagingException;
import java.util.HashMap;

public class NetUtilsTest {

    @Test
    public void testLark() {
        HashMap<String, String> map = Maps.newHashMap();
        map.put("msg_type", "text");
        map.put("content", "{\"text\":\"111\"}");
        HttpClientUtils.doGet("https://open.feishu.cn/open-apis/bot/v2/hook/ebad2359-a31a-46e6-b567-992f62042c4b", map);
    }

    @Test
    public void testEmail() {
        try {
            EmailUtil.sendEmail("ruitao.wei@qq.com", "Test Email", "This is a test email \\n message.");
        } catch (MessagingException e) {
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }
}
