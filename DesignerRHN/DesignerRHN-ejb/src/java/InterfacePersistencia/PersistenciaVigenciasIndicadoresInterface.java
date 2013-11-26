/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.VigenciasIndicadores;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaVigenciasIndicadoresInterface {

    public List<VigenciasIndicadores> indicadoresPersona(BigInteger secuenciaEmpl);

    public void crear(VigenciasIndicadores vigenciasIndicadores);

    public void editar(VigenciasIndicadores vigenciasIndicadores);

    public void borrar(VigenciasIndicadores vigenciasIndicadores);

    public VigenciasIndicadores buscarVigenciaIndicador(Object id);

    public List<VigenciasIndicadores> buscarVigenciasIndicadores();

    public VigenciasIndicadores buscarVigenciaIndicadorSecuencia(BigInteger secuencia);

    public List<VigenciasIndicadores> indicadoresTotalesEmpleadoSecuencia(BigInteger secuenciaEmpl);
}
