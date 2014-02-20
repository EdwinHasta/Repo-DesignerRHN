/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.VigenciasViajeros;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaVigenciasViajerosInterface {

    public void crear(VigenciasViajeros vigenciaViajero);

    public void editar(VigenciasViajeros vigenciaViajero);

    public void borrar(VigenciasViajeros vigenciaViajero);

    public VigenciasViajeros consultarTipoExamen(BigInteger secuencia);

    public List<VigenciasViajeros> consultarVigenciasViajeros();

    public List<VigenciasViajeros> consultarVigenciasViajerosPorEmpleado(BigInteger secEmpleado);
}
