/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.GruposTiposCC;
import Entidades.TiposCentrosCostos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarTiposCentrosCostosInterface {

    public void modificarTipoCentrosCostos(List<TiposCentrosCostos> listTiposEntidadesModificadas);

    public void borrarTiposCentrosCostos(TiposCentrosCostos tipoCentroCosto);

    public void crearTiposCentrosCostos(TiposCentrosCostos tiposCentrosCostos);

    public void buscarTiposCentrosCostos(TiposCentrosCostos tiposCentrosCostos);

    public List<TiposCentrosCostos> mostrarTiposCentrosCostos();

    public TiposCentrosCostos mostrarTipoEntidad(BigInteger secTipoCentrosCostos);

    public List<GruposTiposCC> mostrarGruposTiposCC();

    public Long verificarBorradoCC(BigInteger secuenciaTipoEntidad);

    public Long verificarBorradoVC(BigInteger secuenciaTipoEntidad);

    public Long verificarBorradoRP(BigInteger secuenciaTipoEntidad);
}
