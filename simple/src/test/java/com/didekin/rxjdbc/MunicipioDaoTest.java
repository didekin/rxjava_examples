package com.didekin.rxjdbc;

import com.didekin.Utils;
import com.didekin.comunidad.Municipio;

import org.junit.AfterClass;
import org.junit.Test;

import java.util.List;

import static com.didekin.rxjdbc.MunicipioDao.daoInstance;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * User: pedro@didekin
 * Date: 07/12/16
 * Time: 13:30
 */
public class MunicipioDaoTest {

    @AfterClass
    public static void afterTest(){
        daoInstance.database.close();
    }

    @Test
    public void getTenMunicipiosByProvincia_C() throws Exception
    {
        daoInstance.getTenMunicipiosByProvincia_C((short) 1).subscribe(
                municipios -> {
                    Utils.log("Inside subscriber.");
                    assertThat(municipios.size(), is(10));
                    assertThat(municipios.get(0).getNombre(), is("Alegría-Dulantzi"));
                });
    }

    @Test
    public void getTenMunicipiosByProvincia_B() throws Exception
    {
        daoInstance.getTenMunicipiosByProvincia_B((short) 1).subscribe(
                municipios -> {
                    assertThat(municipios.size(), is(10));
                    assertThat(municipios.get(0).getNombre(), is("Alegría-Dulantzi"));
                });
    }

    @Test
    public void getTenMunicipiosByProvincia() throws Exception
    {
        List<Municipio> municipios10 = daoInstance.getFiveMunicipiosByProvincia((short) 1);
        assertThat(municipios10.size(), is(5));
        assertThat(municipios10.get(0).getNombre(), is("Alegría-Dulantzi"));
    }

}