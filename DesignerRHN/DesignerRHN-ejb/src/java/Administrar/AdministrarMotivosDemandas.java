/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarMotivosDemandasInterface;
import Entidades.MotivosDemandas;
import InterfacePersistencia.PersistenciaMotivosDemandasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarMotivosDemandas implements AdministrarMotivosDemandasInterface {

    @EJB
    PersistenciaMotivosDemandasInterface persistenciaMotivosDemandas;
    private MotivosDemandas motivoDemandaSeleccionada;
    private MotivosDemandas motivoDemanda;
    private List<MotivosDemandas> listMotivoDemanda;

    public void modificarMotivosDemandas(List<MotivosDemandas> listMotivosDemandasModificadas) {
        for (int i = 0; i < listMotivosDemandasModificadas.size(); i++) {
            System.out.println("Administrar Modificando...");
            motivoDemandaSeleccionada = listMotivosDemandasModificadas.get(i);
            persistenciaMotivosDemandas.editar(motivoDemandaSeleccionada);
        }
    }

    public void borrarMotivosDemandas(MotivosDemandas motivoDemanda) {
        persistenciaMotivosDemandas.borrar(motivoDemanda);
    }

    public void crearMotivosDemandas(MotivosDemandas motivoDemanda) {
        persistenciaMotivosDemandas.crear(motivoDemanda);
    }

    public List<MotivosDemandas> mostrarMotivosDemandas() {
        listMotivoDemanda = persistenciaMotivosDemandas.buscarMotivosDemandas();
        return listMotivoDemanda;
    }

    public MotivosDemandas mostrarMotivoDemanda(BigInteger secMotivoDemanda) {
        motivoDemanda = persistenciaMotivosDemandas.buscarMotivoDemanda(secMotivoDemanda);
        return motivoDemanda;
    }

    public BigInteger verificarBorradoDemanda(BigInteger secuenciaEventos) {
        BigInteger verificadorDemanda = null;

        try {
            System.err.println("Secuencia Motivo Demanda " + secuenciaEventos);
            verificadorDemanda = persistenciaMotivosDemandas.contadorDemandas(secuenciaEventos);
        } catch (Exception e) {
            System.err.println("ERROR AdmnistrarMotivoDemanda verificarBorradoDemanda ERROR :" + e);
        } finally {
            return verificadorDemanda;
        }
    }
}
