/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarDeportesInterface;
import Entidades.Deportes;
import InterfacePersistencia.PersistenciaDeportesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarDeportes implements AdministrarDeportesInterface {

    @EJB
    PersistenciaDeportesInterface persistenciaDeportes;
    private Deportes deporteSeleccionado;
    private Deportes deportes;
    private List<Deportes> listDeportes;

    public void modificarDeportes(List<Deportes> listDeportesModificadas) {
        for (int i = 0; i < listDeportesModificadas.size(); i++) {
            System.out.println("Administrar Modificando...");
            deporteSeleccionado = listDeportesModificadas.get(i);
            persistenciaDeportes.editar(deporteSeleccionado);
        }
    }

    public void borrarDeportes(Deportes deportes) {
        persistenciaDeportes.borrar(deportes);
    }

    public void crearDeportes(Deportes deportes) {
        persistenciaDeportes.crear(deportes);
    }

    public List<Deportes> mostrarDeportes() {
        listDeportes = persistenciaDeportes.buscarDeportes();
        return listDeportes;
    }

    public Deportes mostrarDeporte(BigInteger secDeportes) {
        deportes = persistenciaDeportes.buscarDeporte(secDeportes);
        return deportes;
    }

    public BigInteger verificarBorradoVigenciasDeportes(BigInteger secuenciaTiposAuxilios) {
        BigInteger verificarBorradoVigenciasDeportes = null;
        try {
            verificarBorradoVigenciasDeportes = persistenciaDeportes.verificarBorradoVigenciasDeportes(secuenciaTiposAuxilios);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARDEPORTES verificarBorradoVigenciasDeportes ERROR :" + e);
        } finally {
            return verificarBorradoVigenciasDeportes;
        }
    }

    public BigInteger contadorDeportesPersonas(BigInteger secuenciaTiposAuxilios) {
        BigInteger contadorDeportesPersonas = null;
        try {
            contadorDeportesPersonas = persistenciaDeportes.contadorDeportesPersonas(secuenciaTiposAuxilios);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARDEPORTES contadorDeportesPersonas ERROR :" + e);
        } finally {
            return contadorDeportesPersonas;
        }
    }

    public BigInteger contadorParametrosInformes(BigInteger secuenciaTiposAuxilios) {
        BigInteger contadorParametrosInformes = null;
        try {
            contadorParametrosInformes = persistenciaDeportes.contadorParametrosInformes(secuenciaTiposAuxilios);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARDEPORTES contadorParametrosInformes ERROR :" + e);
        } finally {
            return contadorParametrosInformes;
        }
    }
}
