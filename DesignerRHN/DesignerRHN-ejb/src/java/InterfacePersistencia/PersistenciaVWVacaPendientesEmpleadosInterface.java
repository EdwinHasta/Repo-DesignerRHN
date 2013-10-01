/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.VWVacaPendientesEmpleados;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaVWVacaPendientesEmpleadosInterface {
    
    public void crear(VWVacaPendientesEmpleados vacaP);
    public void editar(VWVacaPendientesEmpleados vacaP);
    public void borrar(VWVacaPendientesEmpleados vacaP);
    public List<VWVacaPendientesEmpleados> buscarVacaPendientesEmpleados(BigInteger secuenciaEmpleado);
    public List<VWVacaPendientesEmpleados> vacaEmpleadoPendientes(BigInteger sec);
    public List<VWVacaPendientesEmpleados> vacaEmpleadoDisfrutadas(BigInteger sec);
    
}
