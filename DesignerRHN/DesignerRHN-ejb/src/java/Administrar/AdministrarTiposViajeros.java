/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Tiposviajeros;
import InterfaceAdministrar.AdministrarTiposViajerosInterface;
import InterfacePersistencia.PersistenciaTiposViajerosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarTiposViajeros implements AdministrarTiposViajerosInterface {

    @EJB
    PersistenciaTiposViajerosInterface persistenciaTiposViajeros;

    @Override
    public void modificarTiposViajeros(List<Tiposviajeros> listaTiposViajeros) {
        for (int i = 0; i < listaTiposViajeros.size(); i++) {
            System.out.println("Administrar Modificando...");
            System.out.println("Nombre " + listaTiposViajeros.get(i).getNombre() + " Codigo " + listaTiposViajeros.get(i).getCodigo());
            persistenciaTiposViajeros.editar(listaTiposViajeros.get(i));
        }
    }

    @Override
    public void borrarTiposViajeros(List<Tiposviajeros> listaTiposViajeros) {
        for (int i = 0; i < listaTiposViajeros.size(); i++) {
            System.out.println("Administrar Borrando...");
            System.out.println("Nombre " + listaTiposViajeros.get(i).getNombre() + " Codigo " + listaTiposViajeros.get(i).getCodigo());
            persistenciaTiposViajeros.borrar(listaTiposViajeros.get(i));
        }
    }

    @Override
    public void crearTiposViajeros(List<Tiposviajeros> listaTiposViajeros) {
        for (int i = 0; i < listaTiposViajeros.size(); i++) {
            System.out.println("Administrar Creando...");
            System.out.println("Nombre " + listaTiposViajeros.get(i).getNombre() + " Codigo " + listaTiposViajeros.get(i).getCodigo());
            persistenciaTiposViajeros.crear(listaTiposViajeros.get(i));
        }
    }

    @Override
    public List<Tiposviajeros> consultarTiposViajeros() {
        List<Tiposviajeros> listMotivosCambiosCargos;
        listMotivosCambiosCargos = persistenciaTiposViajeros.consultarTiposViajeros();
        return listMotivosCambiosCargos;
    }

    @Override
    public Tiposviajeros consultarTipoViajero(BigInteger secTiposViajeros) {
        Tiposviajeros subCategoria;
        subCategoria = persistenciaTiposViajeros.consultarSubCategoria(secTiposViajeros);
        return subCategoria;
    }

    @Override
    public BigInteger contarTiposLegalizaciones(BigInteger secTiposViajeros) {
        BigInteger contarTiposLegalizaciones = null;

        try {
            return contarTiposLegalizaciones = persistenciaTiposViajeros.contarTiposLegalizacionesTipoViajero(secTiposViajeros);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarTiposViajeros contarEscalafones ERROR : " + e);
            return null;
        }
    }

    @Override
    public BigInteger contarVigenciasViajeros(BigInteger secTiposViajeros) {
        BigInteger contarVigenciasViajeros = null;

        try {
            return contarVigenciasViajeros = persistenciaTiposViajeros.contarVigenciasViajerosTipoViajero(secTiposViajeros);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarTiposViajeros contarEscalafones ERROR : " + e);
            return null;
        }
    }

}
