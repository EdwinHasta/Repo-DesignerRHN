/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.VigenciasTiposContratos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaVigenciasTiposContratosInterface {
    public void crear(VigenciasTiposContratos vigenciaTipoContrato);
    public void editar(VigenciasTiposContratos vigenciaTipoContrato);
    public void borrar(VigenciasTiposContratos vigenciaTipoContrato);
    public VigenciasTiposContratos buscarVigenciaTipoContrato(Object id);
    public List<VigenciasTiposContratos> buscarVigenciasTiposContratos();
    public List<VigenciasTiposContratos> buscarVigenciaTipoContratoEmpleado(BigInteger secEmpleado);
}
