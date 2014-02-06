/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.DetallesTiposCotizantes;
import Entidades.TiposEntidades;
import InterfaceAdministrar.AdministrarDetallesTiposCotizantesInterface;
import InterfacePersistencia.PersistenciaDetallesTiposCotizantesInterface;
import InterfacePersistencia.PersistenciaTiposEntidadesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarDetallesTiposCotizantes implements AdministrarDetallesTiposCotizantesInterface {

    @EJB
    PersistenciaDetallesTiposCotizantesInterface persistenciaDetallesTiposCotizantes;
    @EJB
    PersistenciaTiposEntidadesInterface persistenciaTiposEntidades; 

    public List<DetallesTiposCotizantes> detallesTiposCotizantes(BigInteger secuenciaTipoCotizante) {
        List<DetallesTiposCotizantes> listaDetallesTiposCotizantes;
        listaDetallesTiposCotizantes = persistenciaDetallesTiposCotizantes.detallesTiposCotizantes(secuenciaTipoCotizante);
        return listaDetallesTiposCotizantes;
    }

    @Override
    public void borrarDetalleTipoCotizante(DetallesTiposCotizantes detallesTiposCotizantes) {
        persistenciaDetallesTiposCotizantes.borrar(detallesTiposCotizantes);
    }

    @Override
    public void crearDetalleTipoCotizante(DetallesTiposCotizantes detallesTiposCotizantes) {
        persistenciaDetallesTiposCotizantes.crear(detallesTiposCotizantes);
    }

    @Override
    public void modificarDetalleTipoCotizante(List<DetallesTiposCotizantes> listaDetallesTiposCotizantesModificar) {
        for (int i = 0; i < listaDetallesTiposCotizantesModificar.size(); i++) {
            System.out.println("Modificando...");
            if (listaDetallesTiposCotizantesModificar.get(i).getTipocotizante().getSecuencia() == null) {
                listaDetallesTiposCotizantesModificar.get(i).setTipocotizante(null);
            }
            if (listaDetallesTiposCotizantesModificar.get(i).getTipoentidad().getSecuencia() == null) {
                listaDetallesTiposCotizantesModificar.get(i).setTipoentidad(null);
            }
            if (listaDetallesTiposCotizantesModificar.get(i).getMinimosml() == null) {
                listaDetallesTiposCotizantesModificar.get(i).setMinimosml(null);
            }
            if (listaDetallesTiposCotizantesModificar.get(i).getMaximosml() == null) {
                listaDetallesTiposCotizantesModificar.get(i).setMaximosml(null);
            }
            persistenciaDetallesTiposCotizantes.editar(listaDetallesTiposCotizantesModificar.get(i));
        }
    }
    
    @Override
    public List<TiposEntidades> lovTiposEntidades() {
        List<TiposEntidades> listaTiposEntidades;
        listaTiposEntidades = persistenciaTiposEntidades.buscarTiposEntidades();
        return listaTiposEntidades;
    }
}
