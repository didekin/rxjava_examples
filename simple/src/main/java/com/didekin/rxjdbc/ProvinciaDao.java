package com.didekin.rxjdbc;

import com.didekin.comunidad.Provincia;
import com.github.davidmoten.rx.jdbc.Database;

import java.util.List;

import rx.Observable;

import static com.didekin.rxjdbc.DaoConstant.dbBuilder;

/**
 * User: pedro@didekin
 * Date: 08/12/16
 * Time: 11:31
 */
public final class ProvinciaDao {

    public static final ProvinciaDao daoInstance = new ProvinciaDao();
    final Database database;

    private ProvinciaDao()
    {
        database = dbBuilder.build();
    }

    public Observable<List<Provincia>> getProvinciasByCa(short caId)
    {
        return database
                .select("select * from provincia where ca_id= ? order by nombre")
                .parameter(caId)
                .get(rs -> new Provincia(
                                rs.getShort("pr_id"),
                                rs.getString("nombre")
                        )
                )
//                .doOnCompleted(database::close)
                .toList();
    }

    public Observable<Provincia> getProvinciasOneByOne(short caId)
    {
        return database
                .select("select * from provincia where ca_id= ? order by nombre")
                .parameter(caId)
                .get(rs -> new Provincia(
                                rs.getShort("pr_id"),
                                rs.getString("nombre")
                        )
                );
//                .doOnCompleted(database::close);
    }

    List<Provincia> getProvinciasByCaList(short caId)
    {
        return database
                .select("select * from provincia where ca_id= ? order by nombre")
                .parameter(caId)
                .get(rs -> new Provincia(
                                rs.getShort("pr_id"),
                                rs.getString("nombre")
                        )
                )
//                .doOnCompleted(database::close)
                .toList()
                .toBlocking()
                .single();
    }
}
