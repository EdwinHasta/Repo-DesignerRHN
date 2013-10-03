/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Contratos;
import Entidades.TiposCotizantes;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface AdministrarContratosInterface {
    public List<Contratos> contratos();
    public List<TiposCotizantes> lovTiposCotizantes();
    public void modificar(List<Contratos> listContratosModificados);
    public void borrar(Contratos contrato);
    public void crear(Contratos contrato);
    public void reproducirContrato(Short codigoOrigen, Short codigoDestino);
}
