/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Monedas;
import InterfaceAdministrar.AdministrarMonedasInterface;
import InterfacePersistencia.PersistenciaMonedasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class AdministrarMonedas implements AdministrarMonedasInterface {

    @EJB
    PersistenciaMonedasInterface persistenciaMonedas;
    private Monedas monedaSeleccionado;
    private Monedas monedas;
    private List<Monedas> listMonedas;

    @Override
    public void modificarMonedas(List<Monedas> listMonedasModificadas) {
        for (int i = 0; i < listMonedasModificadas.size(); i++) {
            System.out.println("Administrar Modificando...");
            monedaSeleccionado = listMonedasModificadas.get(i);
            persistenciaMonedas.editar(monedaSeleccionado);
        }
    }

    @Override
    public void borrarMonedas(Monedas monedas) {
        persistenciaMonedas.borrar(monedas);
    }

    @Override
    public void crearMonedas(Monedas monedas) {
        persistenciaMonedas.crear(monedas);
    }

    @Override
    public List<Monedas> consultarMonedas() {
        listMonedas = persistenciaMonedas.consultarMonedas();
        return listMonedas;
    }

    @Override
    public Monedas consultarMoneda(BigInteger secMoneda) {
        monedas = persistenciaMonedas.consultarMoneda(secMoneda);
        return monedas;
    }

    @Override
    public BigInteger verificarMonedasProyecto(BigInteger secuenciaIdiomas) {
        BigInteger verificadorProyectos = null;
        try {
            System.err.println("Secuencia Borrado Proyecto" + secuenciaIdiomas);
            verificadorProyectos = persistenciaMonedas.contadorProyectos(secuenciaIdiomas);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarMonedas verificarBorradoProyecto ERROR :" + e);
        } finally {
            return verificadorProyectos;
        }
    }
}
