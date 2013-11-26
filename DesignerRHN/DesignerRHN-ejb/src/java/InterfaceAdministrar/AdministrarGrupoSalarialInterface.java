/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.GruposSalariales;
import Entidades.VigenciasGruposSalariales;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface AdministrarGrupoSalarialInterface {

    public List<GruposSalariales> listGruposSalariales();

    public void crearGruposSalariales(List<GruposSalariales> listaCrear);

    public void editarGruposSalariales(List<GruposSalariales> listaEditar);

    public void borrarGruposSalariales(List<GruposSalariales> listaBorrar);

    public List<VigenciasGruposSalariales> lisVigenciasGruposSalarialesSecuencia(BigInteger secuencia);

    public void borrarVigenciasGruposSalariales(List<VigenciasGruposSalariales> lista);

    public void editarVigenciasGruposSalariales(List<VigenciasGruposSalariales> lista);

    public void crearVigenciasGruposSalariales(List<VigenciasGruposSalariales> lista);
}
