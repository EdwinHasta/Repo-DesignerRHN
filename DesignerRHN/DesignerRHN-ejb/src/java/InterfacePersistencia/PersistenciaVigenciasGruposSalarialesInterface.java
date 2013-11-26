/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.VigenciasGruposSalariales;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaVigenciasGruposSalarialesInterface {

    public void crear(VigenciasGruposSalariales vigenciasGruposSalariales);

    public void editar(VigenciasGruposSalariales vigenciasGruposSalariales);

    public void borrar(VigenciasGruposSalariales vigenciasGruposSalariales);

    public VigenciasGruposSalariales buscarVigenciaGrupoSalarial(Object id);

    public List<VigenciasGruposSalariales> buscarVigenciasGruposSalariales();

    public VigenciasGruposSalariales buscarVigenciaGrupoSalarialSecuencia(BigInteger secuencia);

    public List<VigenciasGruposSalariales> buscarVigenciaGrupoSalarialSecuenciaGrupoSal(BigInteger secuencia);
}
