package com.didekin.rxjdbc;

import com.didekin.Utils;
import com.didekin.comunidad.Municipio;
import com.didekin.comunidad.Provincia;
import com.github.davidmoten.rx.jdbc.ConnectionProvider;
import com.github.davidmoten.rx.jdbc.ConnectionProviderPooled;
import com.github.davidmoten.rx.jdbc.Database;

import java.sql.SQLException;
import java.util.List;

import rx.Observable;

import static com.didekin.rxjdbc.DaoConstant.dbBuilder;

/**
 * User: pedro@didekin
 * Date: 07/12/16
 * Time: 12:57
 */
public final class MunicipioDao {

    public static final MunicipioDao daoInstance = new MunicipioDao();
    final Database database;

    private MunicipioDao()
    {
        database = dbBuilder.build();
    }

    public List<Municipio> getFiveMunicipiosByProvincia(short provId)
    {
        return database
                .select("select * from municipio where pr_id= ? order by m_cd limit 5")
                .parameter(provId)
                .get(rs -> new Municipio(
                                rs.getShort("m_cd"),
                                rs.getString("nombre"),
                                new Provincia(rs.getShort("pr_id"))
                        )
                )
                .toList()
                .toBlocking()
                .single();
    }

    Observable<List<Municipio>> getTenMunicipiosByProvincia_B(short provId)
    {
        return database
                .select("select * from municipio where pr_id= ? order by m_cd limit 10")
                .parameter(provId)
                .get(rs -> new Municipio(
                                rs.getShort("m_cd"),
                                rs.getString("nombre"),
                                new Provincia(rs.getShort("pr_id"))
                        )
                )
                .toList();
    }

    Observable<List<Municipio>> getTenMunicipiosByProvincia_C(short provId) throws SQLException
    {
        ConnectionProvider connectionProvider = new ConnectionProviderPooled(DaoConstant.dbUrl, "pedro", "pedro", 1, 5);
        Database localDatabase = new Database(connectionProvider);

        return localDatabase
                .select("select * from municipio where pr_id= ? order by m_cd limit 10")
                .parameter(provId)
                .get(rs -> new Municipio(
                                rs.getShort("m_cd"),
                                rs.getString("nombre"),
                                new Provincia(rs.getShort("pr_id"))
                        )
                )
                .doOnCompleted(() -> {
                    Utils.log("Inside doOnCompleted.");
                    localDatabase.close();
                    database.close();
                    try {
                        // To test that the connection pool should have been shutdown.
                        connectionProvider.get();
                        throw new IllegalStateException("Should have failed.");
                    } catch (Exception e) {
                        Utils.log(e.getMessage());
                    }
                })
                .toList();
    }
}
