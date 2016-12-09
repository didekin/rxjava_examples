package com.didekin.rxjdbc;

import com.didekin.comunidad.Provincia;

import org.junit.AfterClass;
import org.junit.Test;

import java.util.List;

import static com.didekin.Utils.log;
import static com.didekin.rxjdbc.ProvinciaDao.daoInstance;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * User: pedro@didekin
 * Date: 08/12/16
 * Time: 11:43
 */
public class ProvinciaDaoTest {

    @AfterClass
    public static void afterTest()
    {
        daoInstance.database.close();
    }

    @Test
    public void getProvinciasOneByOne() throws Exception
    {

        // It returns Observable<Municipio>
        daoInstance.getProvinciasOneByOne((short) 11)
                .subscribe(
                        provincia -> assertThat(
                                provincia.getNombre().equals("Badajoz")
                                        || provincia.getNombre().equals("Cáceres"),
                                is(true)
                        )
                );
    }

    @Test
    public void getProvinciasByCaList() throws Exception
    {
        // It returns List<Provincia>.
        List<Provincia> provincias = daoInstance.getProvinciasByCaList((short) 11);
        assertThat(provincias.size(), is(2));
        assertThat(provincias.get(0).getNombre(), is("Badajoz"));
    }

    @Test
    public void getProvinciasByCa() throws Exception
    {
        // It returns an Observable<List<Provincia>>.
        daoInstance.getProvinciasByCa((short) 11)
                .subscribe(
                        provincias -> {
                            log("Inside subscriber.");
                            assertThat(provincias.size(), is(2));
                            assertThat(provincias.get(0).getNombre(), is("Badajoz"));
                        }
                );
    }

}