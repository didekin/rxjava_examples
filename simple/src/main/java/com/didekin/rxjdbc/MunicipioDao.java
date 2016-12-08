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

/**
 * User: pedro@didekin
 * Date: 07/12/16
 * Time: 12:57
 */
class MunicipioDao {

    static final MunicipioDao daoInstance = new MunicipioDao();
    private final static String dbUrl = "jdbc:mysql://localhost:3306/rx_examples?useSSL=false";
    private static final Database.Builder dbBuilder = Database.builder()
            .url(dbUrl)
            .pool(1, 5)
            .username("pedro")
            .password("pedro");

    private MunicipioDao()
    {
    }

    List<Municipio> getTenMunicipiosByProvincia(short provId)
    {
        Database database = dbBuilder.build();
        List<Municipio> municipios = database
                .select("select * from municipio where pr_id= ? order by m_cd limit 10")
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

        database.close();
        return municipios;
    }

    Observable<List<Municipio>> getTenMunicipiosByProvincia_B(short provId)
    {
        Database database = dbBuilder.build();

        return database
                .select("select * from municipio where pr_id= ? order by m_cd limit 10")
                .parameter(provId)
                .get(rs -> new Municipio(
                                rs.getShort("m_cd"),
                                rs.getString("nombre"),
                                new Provincia(rs.getShort("pr_id"))
                        )
                )
                .doOnCompleted(database::close)
                .toList();
    }

    Observable<List<Municipio>> getTenMunicipiosByProvincia_C(short provId) throws SQLException
    {
        ConnectionProvider connectionProvider = new ConnectionProviderPooled(dbUrl, "pedro", "pedro", 1, 5);
        Database database = new Database(connectionProvider);

        return database
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
