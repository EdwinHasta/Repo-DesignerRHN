/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarVigenciasPlantasInterface;
import Entidades.VigenciasPlantas;
import InterfacePersistencia.PersistenciaVigenciasPlantasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarVigenciasPlantas implements AdministrarVigenciasPlantasInterface {

    @EJB
    PersistenciaVigenciasPlantasInterface persistenciaVigenciasPlantas;

    public void modificarVigenciasPlantas(List<VigenciasPlantas> listaVigenciasPlantas) {
        for (int i = 0; i < listaVigenciasPlantas.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaVigenciasPlantas.editar(listaVigenciasPlantas.get(i));
        }
    }

    public void borrarVigenciasPlantas(List<VigenciasPlantas> listaVigenciasPlantas) {
        for (int i = 0; i < listaVigenciasPlantas.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaVigenciasPlantas.borrar(listaVigenciasPlantas.get(i));
        }
    }

    public void crearVigenciasPlantas(List<VigenciasPlantas> listaVigenciasPlantas) {
        for (int i = 0; i < listaVigenciasPlantas.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaVigenciasPlantas.crear(listaVigenciasPlantas.get(i));
        }
    }

    public List<VigenciasPlantas> consultarVigenciasPlantas() {
        List<VigenciasPlantas> listMotivosCambiosCargos;
        listMotivosCambiosCargos = persistenciaVigenciasPlantas.consultarVigenciasPlantas();
        return listMotivosCambiosCargos;
    }

    public VigenciasPlantas consultarVigenciaPlanta(BigInteger secVigenciasPlantas) {
        VigenciasPlantas motivoCambioCargo;
        motivoCambioCargo = persistenciaVigenciasPlantas.consultarVigenciaPlanta(secVigenciasPlantas);
        return motivoCambioCargo;
    }

    public BigInteger contarPlantasVigenciaPlanta(BigInteger secVigenciasPlantas) {
        BigInteger contarPlantasVigenciaPlanta = null;

        try {
            return contarPlantasVigenciaPlanta = persistenciaVigenciasPlantas.contarPlantasVigenciaPlanta(secVigenciasPlantas);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarVigenciasPlantas verificarBorradoVNE ERROR :" + e);
            return null;
        }
    }
}
