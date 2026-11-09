package atividade_lp2.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String URL = "jdbc:mariadb://localhost:3306/wuostore";
    private static final String USUARIO = "root";
    private static final String SENHA = "";
    private static Connection con;

    private Conexao() {}

    public static Connection getConnection() throws SQLException {
        if (con == null || con.isClosed()) {            
            con = DriverManager.getConnection(URL, USUARIO, SENHA);
        }
        return con;
    }

    public void fechar() throws SQLException {
        if (con != null && !con.isClosed()) {
            con.close();
        }
        con = null;
    }
}
