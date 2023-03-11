package com.fyp.adp.fypnighthawk.config;

import com.fyp.adp.fypnighthawk.AdpApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdpApplication.class)
public class DorisConfigTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("dorisTemplate")
    private JdbcTemplate dorisTemplate;

    @Test
    public void testDoris() {
        String                    sql  = "SELECT * FROM ods_test.lucky_cdp_rt_member_update_message_record_dupl limit 10";
        List<Map<String, Object>> maps = dorisTemplate.queryForList(sql);
        System.err.println(maps);
        System.err.println(maps.size());
    }

}
