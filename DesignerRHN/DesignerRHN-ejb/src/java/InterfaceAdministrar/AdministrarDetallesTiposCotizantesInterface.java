/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package InterfaceAdministrar;

import Entidades.DetallesTiposCotizantes;
import Entidades.TiposEntidades;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface AdministrarDetallesTiposCotizantesInterface {
    
    public List<DetallesTiposCotizantes> detallesTiposCotizantes(BigInteger secuenciaTipoCotizante);
    public void borrarDetalleTipoCotizante(DetallesTiposCotizantes detallesTiposCotizantes);
    public void crearDetalleTipoCotizante(DetallesTiposCotizantes detallesTiposCotizantes);
    public void modificarDetalleTipoCotizante(List<DetallesTiposCotizantes> listaDetallesTiposCotizantesModificar);
    public List<TiposEntidades> lovTiposEntidades();
    
}
