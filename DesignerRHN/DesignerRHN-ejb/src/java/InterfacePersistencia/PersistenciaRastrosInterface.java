/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Rastros;
import Entidades.RastrosTablas;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaRastrosInterface {
    public List<Rastros> rastrosTabla(BigInteger secRegistro, String nombreTabla);
    public boolean verificarRastroRegistroTabla(BigInteger secRegistro, String nombreTabla);
    public List<Rastros> rastrosTablaHistoricos(String nombreTabla);
    public List<Rastros> rastrosTablaFecha(Date fechaRegistro, String nombreTabla);
    public boolean verificarRastroHistoricoTabla(String nombreTabla);
    public List<Rastros> rastrosTablaHistoricosEliminados(String nombreTabla);
    public List<Rastros> rastrosTablaHistoricosEliminadosEmpleados(String nombreTabla);
    public boolean verificarEmpleadoTabla(String nombreTabla);
}
