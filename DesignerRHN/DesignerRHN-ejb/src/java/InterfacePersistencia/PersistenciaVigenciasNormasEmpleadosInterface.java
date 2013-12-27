/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.VigenciasNormasEmpleados;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaVigenciasNormasEmpleadosInterface {
    public void crear(VigenciasNormasEmpleados normasEmpleados);

    public void editar(VigenciasNormasEmpleados normasEmpleados);

    public void borrar(VigenciasNormasEmpleados normasEmpleados);

    public VigenciasNormasEmpleados buscarVigenciasNormasEmpleado(BigInteger secuencia); //uno solo

    public List<VigenciasNormasEmpleados> buscarVigenciasNormasEmpleadosEmpl(BigInteger secEmpleado); //lista
    
    public List<VigenciasNormasEmpleados> buscarVigenciasNormasEmpleados(); //lista
}
