/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Parametros;
import Entidades.ParametrosEstructuras;
import Entidades.SolucionesNodos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface AdministrarComprobantesInterface {

    public List<Parametros> parametrosComprobantes();
    public ParametrosEstructuras parametroEstructura();
    public List<SolucionesNodos> solucionesNodosEmpleado(BigInteger secEmpleado);
    public List<SolucionesNodos> solucionesNodosEmpleador(BigInteger secEmpleado);
}
