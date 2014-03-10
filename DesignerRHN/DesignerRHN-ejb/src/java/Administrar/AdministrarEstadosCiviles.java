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

    @Override
    public void modificarEstadosCiviles(List<EstadosCiviles> listaEstadosCiviles) {
        for (int i = 0; i < listaEstadosCiviles.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaEstadosCiviles.editar(listaEstadosCiviles.get(i));
        }
    }

    @Override
    public void borrarEstadosCiviles(List<EstadosCiviles> listaEstadosCiviles) {
        for (int i = 0; i < listaEstadosCiviles.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaEstadosCiviles.borrar(listaEstadosCiviles.get(i));
        }
    }

    @Override
    public void crearEstadosCiviles(List<EstadosCiviles> listaEstadosCiviles) {
        for (int i = 0; i < listaEstadosCiviles.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaEstadosCiviles.crear(listaEstadosCiviles.get(i));
        }
    }

    @Override
    public List<EstadosCiviles> consultarEstadosCiviles() {
        List<EstadosCiviles> listEstadosCiviles;
        listEstadosCiviles = persistenciaEstadosCiviles.consultarEstadosCiviles();
        return listEstadosCiviles;
    }

    @Override
    public BigInteger verificarVigenciasEstadosCiviles(BigInteger secuenciaEstadosCiviles) {
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
