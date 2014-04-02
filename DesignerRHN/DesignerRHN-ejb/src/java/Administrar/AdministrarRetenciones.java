/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Retenciones;
import Entidades.VigenciasRetenciones;
import InterfaceAdministrar.AdministrarRetencionesInterface;
import InterfacePersistencia.PersistenciaRetencionesInterface;
import InterfacePersistencia.PersistenciaVigenciasRetencionesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarRetenciones implements AdministrarRetencionesInterface {

    @EJB
    PersistenciaRetencionesInterface persistenciaRetenciones;
    @EJB
    PersistenciaVigenciasRetencionesInterface persistenciaVigenciasRetenciones;

//VIGENCIAS RETENCIONES
    @Override
    public void borrarVigenciaRetencion(VigenciasRetenciones vretenciones) {
        persistenciaVigenciasRetenciones.borrar(vretenciones);
    }

    @Override
    public void crearVigenciaRetencion(VigenciasRetenciones vretenciones) {
        persistenciaVigenciasRetenciones.crear(vretenciones);
    }

    @Override
    public void modificarVigenciaRetencion(List<VigenciasRetenciones> listaVigenciasRetencionesModificar) {
        for (int i = 0; i < listaVigenciasRetencionesModificar.size(); i++) {
            System.out.println("Modificando...");
            if (listaVigenciasRetencionesModificar.get(i).getUvt() == null) {
                listaVigenciasRetencionesModificar.get(i).setUvt(null);
            }
            persistenciaVigenciasRetenciones.editar(listaVigenciasRetencionesModificar.get(i));
        }
    }

    @Override
    public List<VigenciasRetenciones> consultarVigenciasRetenciones() {
        try {
            List<VigenciasRetenciones> actual = persistenciaVigenciasRetenciones.buscarVigenciasRetenciones();
            return actual;
        } catch (Exception e) {
            System.out.println("Error consultarVigenciasRetenciones: " + e.toString());
            return null;
        }
    }

//RETENCIONES
    @Override
    public void borrarRetencion(Retenciones retenciones) {
        persistenciaRetenciones.borrar(retenciones);
    }

    @Override
    public void crearRetencion(Retenciones retenciones) {
        persistenciaRetenciones.crear(retenciones);
    }

    @Override
    public void modificarRetencion(List<Retenciones> listaRetencionesModificar) {
        for (int i = 0; i < listaRetencionesModificar.size(); i++) {
            System.out.println("Modificando...");
            if (listaRetencionesModificar.get(i).getAdicionauvt()== null) {
                listaRetencionesModificar.get(i).setAdicionauvt(null);
            }
            persistenciaRetenciones.editar(listaRetencionesModificar.get(i));
        }
    }

    @Override
    public List<Retenciones> consultarRetenciones(BigInteger secRetencion) {
        try {
            List<Retenciones> actual = persistenciaRetenciones.buscarRetencionesVig(secRetencion);
            return actual;
        } catch (Exception e) {
            System.out.println("Error conceptoActual Admi : " + e.toString());
            return null;
        }
    }

}
