/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.GruposTiposCC;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaGruposTiposCCInterface {

    public void crear(GruposTiposCC gruposTiposCC);

    public void editar(GruposTiposCC gruposTiposCC);

    public void borrar(GruposTiposCC gruposTiposCC);

    public GruposTiposCC buscarGruposTiposCCPorSecuencia(BigInteger secuenciaTiposCentrosCostos);

    public List<GruposTiposCC> buscarGruposTiposCC();
}
