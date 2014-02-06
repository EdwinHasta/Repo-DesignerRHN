/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package InterfacePersistencia;

import Entidades.DetallesTiposCotizantes;
import java.math.BigInteger;
import java.util.List;


public interface PersistenciaDetallesTiposCotizantesInterface {
    
    public void crear(DetallesTiposCotizantes detallesTiposCotizantes);
    public void editar(DetallesTiposCotizantes detallesTiposCotizantes);
    public void borrar(DetallesTiposCotizantes detallesTiposCotizantes);
    public List<DetallesTiposCotizantes> detallesTiposCotizantes(BigInteger tipoCotizante);
    
}
