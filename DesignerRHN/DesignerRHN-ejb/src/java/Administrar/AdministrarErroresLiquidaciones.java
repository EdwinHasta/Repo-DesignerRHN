/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarErroresLiquidacionesInterface;
import Entidades.ErroresLiquidacion;
import Entidades.VigenciasLocalizaciones;
import InterfaceAdministrar.AdministrarSesionesInterface;
import InterfacePersistencia.PersistenciaErroresLiquidacionesInterface;
import InterfacePersistencia.PersistenciaVigenciasLocalizacionesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarErroresLiquidaciones implements AdministrarErroresLiquidacionesInterface {

    @EJB
    PersistenciaErroresLiquidacionesInterface persistenciaErroresLiquidacionesInterface;
    @EJB
    AdministrarSesionesInterface administrarSesiones;
    @EJB
    PersistenciaVigenciasLocalizacionesInterface persistenciaVigenciasLocalicaciones;
    private EntityManager em;

    @Override
    public void obtenerConexion(String idSesion) {
        em = administrarSesiones.obtenerConexionSesion(idSesion);
    }

    @Override
    public List<ErroresLiquidacion> consultarErroresLiquidacionEmpleado(BigInteger secEmpleado) {
        List<ErroresLiquidacion> listaLiquidacionesLog = persistenciaErroresLiquidacionesInterface.consultarErroresLiquidacionPorEmpleado(em, secEmpleado);
        List<VigenciasLocalizaciones> vigenciaSeleccionada;
        if (listaLiquidacionesLog != null || !listaLiquidacionesLog.isEmpty()) {
            for (int i = 0; i < listaLiquidacionesLog.size(); i++) {
                vigenciaSeleccionada = persistenciaVigenciasLocalicaciones.buscarVigenciasLocalizacionesEmpleado(em, listaLiquidacionesLog.get(i).getEmpleado().getSecuencia());
                listaLiquidacionesLog.get(i).setVigenciaLocalizacion(vigenciaSeleccionada.get(0));
            }
        }
        return listaLiquidacionesLog;
    }

    public void borrarErroresLiquidaciones(List<ErroresLiquidacion> listaErroresLiquidacion) {
        System.out.println("ADMINISTRARLIQUDACIONES listaErroresLiquidacion : " + listaErroresLiquidacion.size());
        for (int i = 0; i < listaErroresLiquidacion.size(); i++) {
            persistenciaErroresLiquidacionesInterface.borrar(em, listaErroresLiquidacion.get(i));
        }

    }
}
