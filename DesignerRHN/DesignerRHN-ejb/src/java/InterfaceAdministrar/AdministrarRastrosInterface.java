/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Rastros;
import Entidades.RastrosValores;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface AdministrarRastrosInterface {
    public List<Rastros> rastrosTabla(BigInteger secRegistro, String nombreTabla);
    public int obtenerTabla(BigInteger secRegistro, String nombreTabla);
    public List<Rastros> rastrosTablaHistoricos(String nombreTabla);
    public boolean verificarHistoricosTabla(String nombreTabla);
    public List<Rastros> rastrosTablaHistoricosEliminados(String nombreTabla);
    public List<Rastros> rastrosTablaHistoricosEliminadosEmpleado(String nombreTabla);
    public List<Rastros> rastrosTablaFecha(Date fechaRegistro,String nombreTabla);
    public List<RastrosValores> valorRastro(BigInteger secRastro);
    public boolean existenciaEmpleadoTabla(String nombreTabla);
}
