/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.VigenciasProyectos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaVigenciasProyectosInterface {
    public List<VigenciasProyectos> proyectosPersona(BigInteger secuenciaEmpleado);
}
