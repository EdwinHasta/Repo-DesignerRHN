/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Actividades;
import InterfaceAdministrar.AdministrarActividadesInterface;
import InterfacePersistencia.PersistenciaActividadesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarActividades implements AdministrarActividadesInterface {

    @EJB
    PersistenciaActividadesInterface persistenciaActividades;

    public void modificarActividades(List<Actividades> listaActividades) {
        for (int i = 0; i < listaActividades.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaActividades.editar(listaActividades.get(i));
        }
    }

    public void borrarActividades(List<Actividades> listaActividades) {
        for (int i = 0; i < listaActividades.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaActividades.borrar(listaActividades.get(i));
        }
    }

    public void crearActividades(List<Actividades> listaActividades) {
        for (int i = 0; i < listaActividades.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaActividades.crear(listaActividades.get(i));
        }
    }

    public List<Actividades> consultarActividades() {
        List<Actividades> listActividades;
        listActividades = persistenciaActividades.buscarActividades();
        return listActividades;
    }

    public BigInteger contarBienNecesidadesActividad(BigInteger secuenciaActividades) {
        BigInteger contarParametrosInformesActividad = null;
        try {
            return contarParametrosInformesActividad = persistenciaActividades.contarBienNecesidadesActividad(secuenciaActividades);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARACTIVIDADES contarBienNecesidadesActividad ERROR :" + e);
            return null;
        }
    }
    public BigInteger contarParametrosInformesActividad(BigInteger secuenciaActividades) {
        BigInteger contarParametrosInformesActividad = null;
        try {
            return contarParametrosInformesActividad = persistenciaActividades.contarParametrosInformesActividad(secuenciaActividades);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARACTIVIDADES contarParametrosInformesActividad ERROR :" + e);
            return null;
        }
    }
}
