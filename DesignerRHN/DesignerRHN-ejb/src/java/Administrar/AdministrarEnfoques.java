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

    public void modificarEnfoques(List<Enfoques> listEnfoques) {
        for (int i = 0; i < listEnfoques.size(); i++) {
            System.out.println("Administrar Modificando...");
            PersistenciaEnfoques.editar(listEnfoques.get(i));
        }
    }

    public void borrarEnfoques(List<Enfoques> listEnfoques) {
        for (int i = 0; i < listEnfoques.size(); i++) {
            System.out.println("Administrar Borrando...");
            PersistenciaEnfoques.borrar(listEnfoques.get(i));
        }
    }

    public void crearEnfoques(List<Enfoques> listEnfoques) {
        for (int i = 0; i < listEnfoques.size(); i++) {
            System.out.println("Administrar Creando...");
            PersistenciaEnfoques.crear(listEnfoques.get(i));
        }
    }

    public List<Enfoques> consultarEnfoques() {
        List<Enfoques> listEnfoques;
        listEnfoques = PersistenciaEnfoques.buscarEnfoques();
        return listEnfoques;
    }

    public Enfoques consultarEnfoque(BigInteger secEnfoques) {
        Enfoques enfoques;
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
