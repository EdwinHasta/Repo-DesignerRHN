/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Administrar;

import Entidades.DetallesTiposCotizantes;
import Entidades.TiposCotizantes;
import InterfaceAdministrar.AdministrarTiposCotizantesInterface;
import InterfacePersistencia.PersistenciaTiposCotizantesInterface;
import Persistencia.PersistenciaDetallesTiposCotizantes;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;


/**
 *
 * @author user
 */
@Stateful
public class AdministrarTiposCotizantes implements AdministrarTiposCotizantesInterface{
    @EJB
    PersistenciaTiposCotizantesInterface persistenciaTiposCotizantes;
    

    public List<TiposCotizantes> tiposCotizantes() {
        List<TiposCotizantes> listaTiposCotizantes;
        listaTiposCotizantes = persistenciaTiposCotizantes.lovTiposCotizantes();
        return listaTiposCotizantes;
    }
    
    @Override
    public void borrarTipoCotizante(TiposCotizantes tiposCotizantes) {
        persistenciaTiposCotizantes.borrar(tiposCotizantes);
    }

    @Override
    public void crearTipoCotizante(TiposCotizantes tiposCotizantes) {
        persistenciaTiposCotizantes.crear(tiposCotizantes);
    }
    
    

    @Override
    public void modificarTipoCotizante(List<TiposCotizantes> listaTiposCotizantesModificar) {
        for (int i = 0; i < listaTiposCotizantesModificar.size(); i++) {
            System.out.println("Modificando...");
            if (listaTiposCotizantesModificar.get(i).getCotizapension() == null) {
                        listaTiposCotizantesModificar.get(i).setCotizapension(null);
                    }
                    if (listaTiposCotizantesModificar.get(i).getCotizasalud()== null) {
                        listaTiposCotizantesModificar.get(i).setCotizasalud(null);
                    }
                    if (listaTiposCotizantesModificar.get(i).getCotizariesgo()== null) {
                        listaTiposCotizantesModificar.get(i).setCotizariesgo(null);
                    }
                    if (listaTiposCotizantesModificar.get(i).getCotizaparafiscal()== null) {
                        listaTiposCotizantesModificar.get(i).setCotizaparafiscal(null);
                    }
                    if (listaTiposCotizantesModificar.get(i).getCotizaesap()== null) {
                        listaTiposCotizantesModificar.get(i).setCotizaesap(null);
                    }
                    if (listaTiposCotizantesModificar.get(i).getCotizamen()== null) {
                        listaTiposCotizantesModificar.get(i).setCotizamen(null);
                    }
                    if (listaTiposCotizantesModificar.get(i).getCodigoalternativo()== null) {
                        listaTiposCotizantesModificar.get(i).setCodigoalternativo(null);
                    }
                    if (listaTiposCotizantesModificar.get(i).getSubtipocotizante()== null) {
                        listaTiposCotizantesModificar.get(i).setSubtipocotizante(null);
                    }
                    if (listaTiposCotizantesModificar.get(i).getExtranjero()== null) {
                        listaTiposCotizantesModificar.get(i).setExtranjero(null);
                    }
            persistenciaTiposCotizantes.editar(listaTiposCotizantesModificar.get(i));
        }
    }
    
    
}
