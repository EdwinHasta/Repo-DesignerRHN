/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.TiposCertificados;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarTiposCertificadosInterface {

    public void modificarTiposCertificados(List<TiposCertificados> listNormasLaboralesModificadas);

    public void borrarTiposCertificados(TiposCertificados tiposCertificados);

    public void crearTiposCertificados(TiposCertificados tiposCertificados);

    public List<TiposCertificados> mostrarTiposCertificados();

    public TiposCertificados mostrarTipoCertificado(BigInteger secTipoCertificado);
}
