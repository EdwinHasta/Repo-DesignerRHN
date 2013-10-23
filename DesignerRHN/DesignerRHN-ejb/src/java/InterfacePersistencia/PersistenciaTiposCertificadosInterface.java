/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.TiposCertificados;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaTiposCertificadosInterface {

    public void crear(TiposCertificados motivosMvrs);

    public void editar(TiposCertificados motivosMvrs);

    public void borrar(TiposCertificados motivosMvrs);

    public TiposCertificados buscarTipoCertificado(BigInteger secuenciaTC);

    public List<TiposCertificados> buscarTiposCertificados();
}
