/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarNivelesInterface;
import Entidades.Niveles;
import InterfacePersistencia.PersistenciaNivelesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarNiveles implements AdministrarNivelesInterface {

    @EJB
    PersistenciaNivelesInterface persistenciaNiveles;

    public void modificarNiveles(List<Niveles> listaNiveles) {
        for (int i = 0; i < listaNiveles.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaNiveles.editar(listaNiveles.get(i));
        }
    }

    public void borrarNiveles(List<Niveles> listaNiveles) {
        for (int i = 0; i < listaNiveles.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaNiveles.borrar(listaNiveles.get(i));
        }
    }

    public void crearNiveles(List<Niveles> listaNiveles) {
        for (int i = 0; i < listaNiveles.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaNiveles.crear(listaNiveles.get(i));
        }
    }

    public List<Niveles> consultarNiveles() {
        List<Niveles> listMotivosCambiosCargos;
        listMotivosCambiosCargos = persistenciaNiveles.consultarNiveles();
        return listMotivosCambiosCargos;
    }

    public Niveles consultarNivel(BigInteger secNiveles) {
        Niveles motivoCambioCargo;
        motivoCambioCargo = persistenciaNiveles.consultarNivel(secNiveles);
        return motivoCambioCargo;
    }

    public BigInteger contarEvalConvocatoriasNivel(BigInteger secNiveles) {
        BigInteger contarEvalConvocatoriasNivel = null;

        try {
            return contarEvalConvocatoriasNivel = persistenciaNiveles.contarEvalConvocatoriasNivel(secNiveles);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarNiveles contarEvalConvocatoriasNivel ERROR :" + e);
            return null;
        }
    }

    public BigInteger contarPlantasNivel(BigInteger secNiveles) {
        BigInteger contarPlantasNivel = null;

        try {
            return contarPlantasNivel = persistenciaNiveles.contarPlantasNivel(secNiveles);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarNiveles contarPlantasNivel ERROR :" + e);
            return null;
        }
    }

    public BigInteger contarPlantasPersonalesNivel(BigInteger secNiveles) {
        BigInteger contarPlantasPersonalesNivel = null;

        try {
            return contarPlantasPersonalesNivel = persistenciaNiveles.contarPlantasPersonalesNivel(secNiveles);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarNiveles verificarBorradoVNE ERROR :" + e);
            return null;
        }
    }
}
