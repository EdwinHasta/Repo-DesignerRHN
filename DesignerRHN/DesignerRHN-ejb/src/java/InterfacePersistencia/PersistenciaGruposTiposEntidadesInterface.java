/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Grupostiposentidades;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaGruposTiposEntidadesInterface {

    public void crear(Grupostiposentidades gruposTiposEntidades);

    public void editar(Grupostiposentidades gruposTiposEntidades);

    public void borrar(Grupostiposentidades gruposTiposEntidades);

    public Grupostiposentidades buscarGrupoTipoEntidad(BigInteger secuenciaGruposTiposEntidades);

    public List<Grupostiposentidades> buscarGruposTiposEntidades();
}
