/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Administrar;

import InterfaceAdministrar.AdministrarElementosCausasAccidentesInterface;
import Entidades.ElementosCausasAccidentes;
import InterfacePersistencia.PersistenciaElementosCausasAccidentesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarElementosCausasAccidentes implements AdministrarElementosCausasAccidentesInterface {

     @EJB
    PersistenciaElementosCausasAccidentesInterface persistenciaElementosCausasAccidentes;
    private ElementosCausasAccidentes elementosCausasAccidentesSeleccionada;
    private ElementosCausasAccidentes elementosCausasAccidentes;
    private List<ElementosCausasAccidentes> listElementosCausasAccidentes;
  

    public void modificarElementosCausasAccidentes(List<ElementosCausasAccidentes> listElementosCausasAccidentes) {
        for (int i = 0; i < listElementosCausasAccidentes.size(); i++) {
            System.out.println("Administrar Modificando...");
            elementosCausasAccidentesSeleccionada = listElementosCausasAccidentes.get(i);
            persistenciaElementosCausasAccidentes.editar(elementosCausasAccidentesSeleccionada);
        }
    }

    public void borrarElementosCausasAccidentes(ElementosCausasAccidentes elementosCausasAccidentes) {
        persistenciaElementosCausasAccidentes.borrar(elementosCausasAccidentes);
    }

    public void crearElementosCausasAccidentes(ElementosCausasAccidentes elementosCausasAccidentes) {
        persistenciaElementosCausasAccidentes.crear(elementosCausasAccidentes);
    }

    public List<ElementosCausasAccidentes> mostrarElementosCausasAccidentes() {
        listElementosCausasAccidentes = persistenciaElementosCausasAccidentes.buscarElementosCausasAccidentes();
        return listElementosCausasAccidentes;
    }

    public ElementosCausasAccidentes mostrarElementoCausaAccidente(BigInteger secElementosCausasAccidentes) {
        elementosCausasAccidentes = persistenciaElementosCausasAccidentes.buscarElementoCausaAccidente(secElementosCausasAccidentes);
        return elementosCausasAccidentes;
    }
    
    public BigInteger contadorSoAccidentes(BigInteger secuenciaTiposAuxilios) {
        BigInteger contadorSoAccidentes = null;
        try {
            contadorSoAccidentes = persistenciaElementosCausasAccidentes.contadorSoAccidentes(secuenciaTiposAuxilios);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARELEMENTOSCAUSASACCIDENTES contadorSoAccidentes ERROR :" + e);
        } finally {
            return contadorSoAccidentes;
        }
    }

    public BigInteger contadorSoAccidentesMedicos(BigInteger secuenciaTiposAuxilios) {
        BigInteger contadorSoAccidentesMedicos = null;
        try {
            contadorSoAccidentesMedicos = persistenciaElementosCausasAccidentes.contadorSoAccidentesMedicos(secuenciaTiposAuxilios);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARELEMENTOSCAUSASACCIDENTES contadorSoAccidentesMedicos ERROR :" + e);
        } finally {
            return contadorSoAccidentesMedicos;
        }
    }

    public BigInteger contadorSoIndicadoresFr(BigInteger secuenciaTiposAuxilios) {
        BigInteger contadorSoIndicadoresFr = null;
        try {
            contadorSoIndicadoresFr = persistenciaElementosCausasAccidentes.contadorSoIndicadoresFr(secuenciaTiposAuxilios);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARELEMENTOSCAUSASACCIDENTES contadorSoIndicadoresFr ERROR :" + e);
        } finally {
            return contadorSoIndicadoresFr;
        }
    }
}
