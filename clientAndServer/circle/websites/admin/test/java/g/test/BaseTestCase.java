package g.test;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.TimeZone;


/**
 * Created by Kevice on 2015/1/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:conf/*-appCtx.xml")
@Transactional
public abstract class BaseTestCase extends Mockito {

	@BeforeClass
	public static void beforeClass(){
		TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
	}

}
