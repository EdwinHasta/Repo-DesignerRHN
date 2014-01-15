/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarEventosInterface;
import Entidades.Eventos;
import InterfacePersistencia.PersistenciaEventosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarEventos implements AdministrarEventosInterface {

    @EJB
    PersistenciaEventosInterface persistenciaEventos;

    @Override
    public void modificarEventos(List<Eventos> listaEventos) {
        for (int i = 0; i < listaEventos.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaEventos.editar(listaEventos.get(i));
        }
    }

    @Override
    public void borrarEventos(List<Eventos> listaEventos) {
        for (int i = 0; i < listaEventos.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaEventos.borrar(listaEventos.get(i));
        }
    }

    @Override
    public void crearEventos(List<Eventos> listaEventos) {
        for (int i = 0; i < listaEventos.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaEventos.crear(listaEventos.get(i));
        }
    }

    @Override
    public List<Eventos> consultarEventos() {
        List<Eventos> listEventos;
        listEventos = persistenciaEventos.buscarEventos();
        return listEventos;
    }

    @Override
    public Eventos consultarEvento(BigInteger secDeportes) {
        Eventos eventos;
        eventos = persistenciaEventos.buscarEvento(secDeportes);
        return eventos;
    }

    @Override
    public BigInteger verificarVigenciasEventos(BigInteger secuenciaEventos) {
        BigInteger verificadorVigenciasEventos = null;
        try {
            System.err.println("Secuencia VigenciasEventos " + secuenciaEventos);
            verificadorVigenciasEventos = persistenciaEventos.contadorVigenciasEventos(secuenciaEventos);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarEventos VigenciasEstadoCiviles ERROR :" + e);
        } finally {
            return verificadorVigenciasEventos;
        }
    }

}
