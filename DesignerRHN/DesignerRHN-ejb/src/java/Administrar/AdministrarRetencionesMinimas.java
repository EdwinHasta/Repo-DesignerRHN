/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Administrar;

import Entidades.RetencionesMinimas;
import Entidades.VigenciasRetencionesMinimas;
import InterfaceAdministrar.AdministrarRetencionesMinimasInterface;
import InterfacePersistencia.PersistenciaRetencionesMinimasInterface;
import InterfacePersistencia.PersistenciaVigenciasRetencionesMinimasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarRetencionesMinimas implements AdministrarRetencionesMinimasInterface{

    @EJB
    PersistenciaRetencionesMinimasInterface persistenciaRetencionesMinimas;
    @EJB
    PersistenciaVigenciasRetencionesMinimasInterface persistenciaVigenciasRetencionesMinimas;

//VIGENCIAS RETENCIONES
    @Override
    public void borrarVigenciaRetencion(VigenciasRetencionesMinimas vretenciones) {
        persistenciaVigenciasRetencionesMinimas.borrar(vretenciones);
    }

    @Override
    public void crearVigenciaRetencion(VigenciasRetencionesMinimas vretenciones) {
        persistenciaVigenciasRetencionesMinimas.crear(vretenciones);
    }

    @Override
    public void modificarVigenciaRetencion(List<VigenciasRetencionesMinimas> listaVigenciasRetencionesModificar) {
        for (int i = 0; i < listaVigenciasRetencionesModificar.size(); i++) {
            System.out.println("Modificando...");
            persistenciaVigenciasRetencionesMinimas.editar(listaVigenciasRetencionesModificar.get(i));
        }
    }

    @Override
    public List<VigenciasRetencionesMinimas> consultarVigenciasRetenciones() {
        try {
            List<VigenciasRetencionesMinimas> actual = persistenciaVigenciasRetencionesMinimas.buscarVigenciasRetencionesMinimas();
            return actual;
        } catch (Exception e) {
            System.out.println("Error consultarVigenciasRetenciones: " + e.toString());
            return null;
        }
    }

//RETENCIONES
    @Override
    public void borrarRetencion(RetencionesMinimas retenciones) {
        persistenciaRetencionesMinimas.borrar(retenciones);
    }

    @Override
    public void crearRetencion(RetencionesMinimas retenciones) {
        persistenciaRetencionesMinimas.crear(retenciones);
    }

    @Override
    public void modificarRetencion(List<RetencionesMinimas> listaRetencionesModificar) {
        for (int i = 0; i < listaRetencionesModificar.size(); i++) {
            System.out.println("Modificando...");
            
            persistenciaRetencionesMinimas.editar(listaRetencionesModificar.get(i));
        }
    }

    @Override
    public List<RetencionesMinimas> consultarRetenciones(BigInteger secRetencion) {
        try {
            List<RetencionesMinimas> actual = persistenciaRetencionesMinimas.buscarRetencionesMinimasVig(secRetencion);
            return actual;
        } catch (Exception e) {
            System.out.println("Error consultarRetenciones Admi : " + e.toString());
            return null;
        }
    }
}
