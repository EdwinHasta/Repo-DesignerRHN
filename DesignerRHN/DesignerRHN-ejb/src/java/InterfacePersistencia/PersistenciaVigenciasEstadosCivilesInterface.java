/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.VigenciasEstadosCiviles;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaVigenciasEstadosCivilesInterface {
    public void crear(VigenciasEstadosCiviles vigenciasEstadosCiviles);
    public void editar(VigenciasEstadosCiviles vigenciasEstadosCiviles);
    public void borrar(VigenciasEstadosCiviles vigenciasEstadosCiviles);
    public VigenciasEstadosCiviles buscarVigenciaEstadoCivil(BigInteger id);
    public List<VigenciasEstadosCiviles> buscarVigenciasEstadosCiviles();
    public List<VigenciasEstadosCiviles> vigenciaEstadoCivilPersona(BigInteger secuenciaPersona);
}
