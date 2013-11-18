/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.GruposInfAdicionales;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaGruposInfAdicionalesInterface {

    public void crear(GruposInfAdicionales gruposInfAdicionales);

    public void editar(GruposInfAdicionales gruposInfAdicionales);

    public void borrar(GruposInfAdicionales gruposInfAdicionales);

    public GruposInfAdicionales buscarGrupoInfAdicional(Object id);

    public List<GruposInfAdicionales> buscarGruposInfAdicionales();
}
