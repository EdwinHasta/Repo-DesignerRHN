/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.EvalActividades;
import InterfaceAdministrar.AdministrarEvalActividadesInterface;
import InterfacePersistencia.PersistenciaEvalActividadesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarEvalActividades implements AdministrarEvalActividadesInterface {

    @EJB
    PersistenciaEvalActividadesInterface persistenciaEvalActividades;

    public void modificarEvalActividades(List<EvalActividades> listaEvalActividades) {
        for (int i = 0; i < listaEvalActividades.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaEvalActividades.editar(listaEvalActividades.get(i));
        }
    }

    public void borrarEvalActividades(List<EvalActividades> listaEvalActividades) {
        for (int i = 0; i < listaEvalActividades.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaEvalActividades.borrar(listaEvalActividades.get(i));
        }
    }

    public void crearEvalActividades(List<EvalActividades> listaEvalActividades) {
        for (int i = 0; i < listaEvalActividades.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaEvalActividades.crear(listaEvalActividades.get(i));
        }
    }

    public List<EvalActividades> consultarEvalActividades() {
        List<EvalActividades> listMotivosCambiosCargos;
        listMotivosCambiosCargos = persistenciaEvalActividades.consultarEvalActividades();
        return listMotivosCambiosCargos;
    }

    public EvalActividades consultarEvalActividad(BigInteger secEvalActividades) {
        EvalActividades subCategoria;
        subCategoria = persistenciaEvalActividades.consultarEvalActividad(secEvalActividades);
        return subCategoria;
    }

    public BigInteger contarCapBuzonesEvalActividad(BigInteger secEvalActividades) {
        BigInteger contarCapBuzonesEvalActividad = null;

        try {
            return contarCapBuzonesEvalActividad = persistenciaEvalActividades.contarCapBuzonesEvalActividad(secEvalActividades);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarEvalActividades contarCapBuzonesEvalActividad ERROR : " + e);
            return null;
        }
    }

    public BigInteger contarCapNecesidadesEvalActividad(BigInteger secEvalActividades) {
        BigInteger contarCapNecesidadesEvalActividad = null;

        try {
            return contarCapNecesidadesEvalActividad = persistenciaEvalActividades.contarCapNecesidadesEvalActividad(secEvalActividades);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarEvalActividades contarCapNecesidadesEvalActividad ERROR : " + e);
            return null;
        }
    }

    public BigInteger contarEvalPlanesDesarrollosEvalActividad(BigInteger secEvalActividades) {
        BigInteger contarEvalPlanesDesarrollosEvalActividad = null;

        try {
            return contarEvalPlanesDesarrollosEvalActividad = persistenciaEvalActividades.contarEvalPlanesDesarrollosEvalActividad(secEvalActividades);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarEvalActividades contarEvalPlanesDesarrollosEvalActividad ERROR : " + e);
            return null;
        }
    }

}
