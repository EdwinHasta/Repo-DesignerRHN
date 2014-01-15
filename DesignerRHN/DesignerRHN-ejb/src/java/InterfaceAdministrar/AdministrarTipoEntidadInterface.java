/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Grupostiposentidades;
import Entidades.TiposEntidades;
import Entidades.VigenciasAfiliaciones;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarTipoEntidadInterface {

    public void modificarTipoEntidad(List<TiposEntidades> listTiposEntidadesModificadas);

    public void borrarTipoEntidad(TiposEntidades tipoEntidad);

    public void crearTipoEntidad(TiposEntidades tiposEntidades);

    public void buscarTipoEntidad(TiposEntidades tiposEntidades);

    public List<TiposEntidades> mostrarTiposEntidades();

    public TiposEntidades mostrarTipoEntidad(BigInteger secTipoEntidad);

    public List<Grupostiposentidades> mostrarGruposTiposEntidades();

    public List<VigenciasAfiliaciones> mostarVigenciasAfiliaciones();

    /**
     *
     * @param secuenciaTipoEntidad
     * @return
     */
    public BigInteger verificarBorrado(BigInteger secuenciaTipoEntidad);

    public BigInteger verificarBorradoFCE(BigInteger secuenciaTipoEntidad);
}
