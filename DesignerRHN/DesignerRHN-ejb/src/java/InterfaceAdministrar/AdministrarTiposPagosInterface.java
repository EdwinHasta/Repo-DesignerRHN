/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Tipospagos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarTiposPagosInterface {

    public void modificarTiposPagos(List<Tipospagos> listaTiposPagos);

    public void borrarTiposPagos(List<Tipospagos> listaTiposPagos);

    public void crearTiposPagos(List<Tipospagos> listaTiposPagos);

    public List<Tipospagos> consultarTiposPagos();

    public Tipospagos consultarTipoPago(BigInteger secTiposPagos);

    public BigInteger contarProcesosTipoPago(BigInteger secTiposPagos);
}
