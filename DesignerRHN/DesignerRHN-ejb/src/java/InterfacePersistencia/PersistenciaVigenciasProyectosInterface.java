/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.VigenciasProyectos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaVigenciasProyectosInterface {

    public List<VigenciasProyectos> proyectosPersona(BigInteger secuenciaEmpleado);

    public void crear(VigenciasProyectos vigenciasProyectos);

    public void editar(VigenciasProyectos vigenciasProyectos);

    public void borrar(VigenciasProyectos vigenciasProyectos);

    public List<VigenciasProyectos> buscarVigenciasFormales();

    public List<VigenciasProyectos> vigenciasProyectosEmpleado(BigInteger secuenciaEmpleado);
}
