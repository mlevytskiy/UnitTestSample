package org.unittestsample.database;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.File;
import java.util.List;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;

@Config(emulateSdk = 18, manifest = "src/main/AndroidManifest.xml")
@RunWith(RobolectricTestRunner.class)
public class DAOTest {

    private DAO dao;
    private PodamFactory podamFactory;

    @Before
    public void setUp() throws Exception {
        dao = DAOImpl.getInstance(Robolectric.application);
        podamFactory = new PodamFactoryImpl();
    }

    @Test
    public void testGetTransactionById() throws Exception {
        Transaction transaction1 = podamFactory.manufacturePojo(Transaction.class);
        Transaction transaction2 = podamFactory.manufacturePojo(Transaction.class);
        dao.save(transaction1);
        dao.save(transaction2);
        List<Transaction> transactions = dao.getAllTransactions();
        assertThat(2).isEqualTo(transactions.size());
    }

    @Test
    public void testReadFile() throws Exception {
        File file = new File(this.getClass().getClassLoader().getResource("test.txt").getFile());
        assertThat(contentOf(file)).isEqualTo("some text");
    }

}