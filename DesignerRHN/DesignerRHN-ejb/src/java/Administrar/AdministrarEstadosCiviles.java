/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarEstadosCivilesInterface;
import Entidades.EstadosCiviles;
import InterfacePersistencia.PersistenciaEstadosCivilesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class AdministrarEstadosCiviles implements AdministrarEstadosCivilesInterface {

    @EJB
    PersistenciaEstadosCivilesInterface persistenciaEstadosCiviles;
    private EstadosCiviles estadoCivilSeleccionado;
    private EstadosCiviles estadoCivil;
    private List<EstadosCiviles> listEstadosCiviles;

    public void modificarEstadosCiviles(List<EstadosCiviles> listDeportesModificadas) {
        for (int i = 0; i < listDeportesModificadas.size(); i++) {
            System.out.println("Administrar Modificando...");
            estadoCivilSeleccionado = listDeportesModificadas.get(i);
            persistenciaEstadosCiviles.editar(estadoCivilSeleccionado);
        }
    }

    public void borrarEstadosCiviles(EstadosCiviles deportes) {
        persistenciaEstadosCiviles.borrar(deportes);
    }

    public void crearEstadosCiviles(EstadosCiviles deportes) {
        persistenciaEstadosCiviles.crear(deportes);
    }

    public List<EstadosCiviles> mostrarEstadosCiviles() {
        listEstadosCiviles = persistenciaEstadosCiviles.buscarEstadosCiviles();
        return listEstadosCiviles;
    }

    public EstadosCiviles mostrarEstadoCivil(BigInteger secDeportes) {
        estadoCivil = persistenciaEstadosCiviles.buscarEstadoCivil(secDeportes);
        return estadoCivil;
    }

    public BigInteger verificarBorradoVigenciasEstadoCiviles(BigInteger secuenciaEstadosCiviles) {
        BigInteger verificadorVigenciasEstadosCiviles = null;
        try {
            System.err.println("Secuencia verificarBorradoVigenciasEstadoCiviles  " + secuenciaEstadosCiviles);
            verificadorVigenciasEstadosCiviles = persistenciaEstadosCiviles.contadorVigenciasEstadosCiviles(secuenciaEstadosCiviles);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarEstadosCiviles verificarBorradoVigenciasEstadoCiviles ERROR :" + e);
        } finally {
            return verificadorVigenciasEstadosCiviles;
        }
    }
}
