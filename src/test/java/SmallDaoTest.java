/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.algoboss.integration.small.dao.SmallDao;
import com.algoboss.integration.small.dao.SmallDaoImpl;
import com.algoboss.integration.small.entity.OrdemServico2;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Agnaldo
 */
public class SmallDaoTest {
    //@Inject
    private SmallDao smallDao;
    
    public SmallDaoTest() {
    }
    //@Test
    public void saveOs(){
        try {
            EntityManager em  = SmallDaoImpl.CreateEm();
            OrdemServico2 os = new OrdemServico2();
            os.setCliente("teste");
            SmallDaoImpl.saveImpl(em.getTransaction(),em, os);            
        } catch (Throwable ex) {
            Logger.getLogger(SmallDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}