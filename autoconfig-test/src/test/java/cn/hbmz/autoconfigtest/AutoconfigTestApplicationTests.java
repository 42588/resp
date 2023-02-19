package cn.hbmz.autoconfigtest;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class AutoconfigTestApplicationTests {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test
	void contextLoads() {
		logger.trace("------Trance-------级别");
		logger.debug("------Debug--------级别");
		logger.info("------------Info---------级别");
		logger.warn("------------Warn----------级别");
		logger.error("Error-----------级别");
	}

}
