/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.GruposSalariales;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaGruposSalarialesInterface {

    public void crear(GruposSalariales gruposSalariales);

    public void editar(GruposSalariales gruposSalariales);

    public void borrar(GruposSalariales gruposSalariales);

    public GruposSalariales buscarGrupoSalarial(Object id);

    public List<GruposSalariales> buscarGruposSalariales();

    public GruposSalariales buscarGrupoSalarialSecuencia(BigInteger secuencia);
}
