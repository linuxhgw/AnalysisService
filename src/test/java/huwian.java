import edu.jhun.dao.InstanceDAO;
import edu.jhun.service.AlgorithmService;
import edu.jhun.utils.DBHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.AnalysisService.AppAnalysis;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.sql.Connection;

/**
 * Created by hgw on 2018/5/26.
 */



@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppAnalysis.class)
@WebAppConfiguration
public class huwian {

    private AlgorithmService studentService =new AlgorithmService();

    private Connection connection = DBHelper.getConnection();
    @Test
    public void likeName() {
        studentService.getLinearRegression(connection,"datacollect1-3-1-3","1-50");
//      assertTrue(studentService.likeName("小明2").size() > 0);
    }

}
