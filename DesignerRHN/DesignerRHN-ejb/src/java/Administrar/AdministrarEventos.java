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
    private Eventos eventoSeleccionado;
    private Eventos eventos;
    private List<Eventos> listEventos;

    public void modificarEventos(List<Eventos> listDeportesModificadas) {
        for (int i = 0; i < listDeportesModificadas.size(); i++) {
            System.out.println("Administrar Modificando...");
            eventoSeleccionado = listDeportesModificadas.get(i);
            persistenciaEventos.editar(eventoSeleccionado);
        }
    }

    public void borrarEventos(Eventos deportes) {
        persistenciaEventos.borrar(deportes);
    }

    public void crearEventos(Eventos deportes) {
        persistenciaEventos.crear(deportes);
    }

    public List<Eventos> mostrarEventos() {
        listEventos = persistenciaEventos.buscarEventos();
        return listEventos;
    }

    public Eventos mostrarEvento(BigInteger secDeportes) {
        eventos = persistenciaEventos.buscarEvento(secDeportes);
        return eventos;
    }

    public BigInteger verificarBorradoVigenciasEventos(BigInteger secuenciaEventos) {
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
