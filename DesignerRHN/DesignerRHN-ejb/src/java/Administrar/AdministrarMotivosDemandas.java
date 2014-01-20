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

    @Override
    public void modificarMotivosDemandas(List<MotivosDemandas> listMotivosDemandas) {
        for (int i = 0; i < listMotivosDemandas.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaMotivosDemandas.editar(listMotivosDemandas.get(i));
        }
    }

    @Override
    public void borrarMotivosDemandas(List<MotivosDemandas> listMotivosDemandas) {
        for (int i = 0; i < listMotivosDemandas.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaMotivosDemandas.borrar(listMotivosDemandas.get(i));
        }
    }

    @Override
    public void crearMotivosDemandas(List<MotivosDemandas> listMotivosDemandas) {
        for (int i = 0; i < listMotivosDemandas.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaMotivosDemandas.crear(listMotivosDemandas.get(i));
        }
    }

    @Override
    public List<MotivosDemandas> consultarMotivosDemandas() {
        List<MotivosDemandas> listMotivoDemanda;
        listMotivoDemanda = persistenciaMotivosDemandas.buscarMotivosDemandas();
        return listMotivoDemanda;
    }

    @Override
    public MotivosDemandas consultarMotivoDemanda(BigInteger secMotivoDemanda) {
        MotivosDemandas motivoDemanda;
        motivoDemanda = persistenciaMotivosDemandas.buscarMotivoDemanda(secMotivoDemanda);
        return motivoDemanda;
    }

    @Override
    public BigInteger contarDemandasMotivoDemanda(BigInteger secuenciaEventos) {
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
