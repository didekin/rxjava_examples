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
final class DaoConstant {


    final static String dbUrl = "jdbc:mysql://localhost:3306/rx_examples?useSSL=false";
    static final Database.Builder dbBuilder = Database.builder()
            .url(dbUrl)
            .pool(1, 5)
            .username("pedro")
            .password("pedro");

    private DaoConstant()
    {
    }
}
