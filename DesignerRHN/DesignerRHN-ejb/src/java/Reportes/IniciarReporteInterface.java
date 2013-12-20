/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Reportes;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Administrator
 */
public interface IniciarReporteInterface {
    public void inicarConexion(String url, String driver, String user, String psw);
    public void ejecutarReporte(Connection conection, BigInteger codigoEmpleado);
    public void cerrarConexion();
    public void ejecutarReporteXLSX();
    public void inicarC();
    public void ejecutarReporteXML();
}
