package com.didekin.rxjava;

import com.didekin.Utils;
import com.didekin.rxjdbc.MunicipioDao;
import com.didekin.rxjdbc.ProvinciaDao;

import rx.Observable;

import static com.didekin.Utils.log;

/**
 * User: pedro@didekin
 * Date: 07/12/16
 * Time: 17:07
 */
public class OperatorWithDb {

    private static void getMunicipiosByCA_1(short caId)
    {
        log("============== Inside getMunicipiosByCA_1() ===============");

        ProvinciaDao.daoInstance.getProvinciasOneByOne(caId)
                .map(provincia -> MunicipioDao.daoInstance.getFiveMunicipiosByProvincia(provincia.getProvinciaId()))
                .flatMap(Observable::from)
                .subscribe(municipio -> Utils.log(municipio.getNombre()));
    }

    private static void getMunicipiosByCA_2(short caId)
    {
        log("============== Inside getMunicipiosByCA_2() ===============");

        ProvinciaDao.daoInstance.getProvinciasOneByOne(caId)
                .flatMapIterable(
                        provincia -> MunicipioDao.daoInstance.getFiveMunicipiosByProvincia(provincia.getProvinciaId())
                )
                .subscribe(municipio -> Utils.log(municipio.getNombre()));
    }

    public static void main(String[] args)
    {
        getMunicipiosByCA_1((short) 11);
        getMunicipiosByCA_2((short) 11);
    }
}
