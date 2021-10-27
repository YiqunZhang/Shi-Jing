package asia.zyq.shijing.Utils;

import asia.zyq.shijing.utils.SmsMessage;
import org.junit.jupiter.api.Test;

public class SmsMessageTest {
    @Test
    public void send(){
        SmsMessage.send("15098144981","123456");
    }
}
