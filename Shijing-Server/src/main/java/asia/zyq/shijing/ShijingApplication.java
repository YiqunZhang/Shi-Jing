package asia.zyq.shijing;

import asia.zyq.shijing.utils.EnergyAdder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
public class ShijingApplication {

    public static void main(String[] args) {

        SpringApplication.run(ShijingApplication.class, args);
        (new EnergyAdder()).run();

    }

}
