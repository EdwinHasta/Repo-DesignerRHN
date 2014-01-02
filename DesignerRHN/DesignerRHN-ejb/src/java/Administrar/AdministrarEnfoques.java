/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarEnfoquesInterface;
import Entidades.Enfoques;
import InterfacePersistencia.PersistenciaEnfoquesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarEnfoques implements AdministrarEnfoquesInterface {

    @EJB
    PersistenciaEnfoquesInterface PersistenciaEnfoques;
    private Enfoques EnfoqueSeleccionado;
    private Enfoques enfoques;
    private List<Enfoques> listEnfoques;

    public void modificarEnfoques(List<Enfoques> listEnfoquesModificadas) {
        for (int i = 0; i < listEnfoquesModificadas.size(); i++) {
            System.out.println("Administrar Modificando...");
            EnfoqueSeleccionado = listEnfoquesModificadas.get(i);
            PersistenciaEnfoques.editar(EnfoqueSeleccionado);
        }
    }

    public void borrarEnfoques(Enfoques motivosLocalizaciones) {
        PersistenciaEnfoques.borrar(motivosLocalizaciones);
    }

    public void crearEnfoques(Enfoques motivosLocalizaciones) {
        PersistenciaEnfoques.crear(motivosLocalizaciones);
    }

    public void buscarEnfoques(Enfoques motivosLocalizaciones) {
        PersistenciaEnfoques.crear(motivosLocalizaciones);
    }

    public List<Enfoques> mostrarEnfoques() {
        listEnfoques = PersistenciaEnfoques.buscarEnfoques();
        return listEnfoques;
    }

    public Enfoques mostrarEnfoque(BigInteger secEnfoques) {
        enfoques = PersistenciaEnfoques.buscarEnfoque(secEnfoques);
        return enfoques;
    }

    public BigInteger verificarTiposDetalles(BigInteger secuenciaTiposAuxilios) {
        BigInteger verificarTiposDetalles = null;
        try {
            verificarTiposDetalles = PersistenciaEnfoques.contadorTiposDetalles(secuenciaTiposAuxilios);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRAREVALPLANILLAS verificarTiposDetalles ERROR :" + e);
        } finally {
            return verificarTiposDetalles;
        }
    }
}
