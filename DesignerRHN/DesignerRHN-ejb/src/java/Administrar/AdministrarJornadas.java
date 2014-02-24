/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Jornadas;
import InterfaceAdministrar.AdministrarJornadasInterface;
import InterfacePersistencia.PersistenciaJornadasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarJornadas implements AdministrarJornadasInterface {

    @EJB
    PersistenciaJornadasInterface persistenciaJornadas;

    public void modificarJornadas(List<Jornadas> listaJornadas) {
        for (int i = 0; i < listaJornadas.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaJornadas.editar(listaJornadas.get(i));
        }
    }

    public void borrarJornadas(List<Jornadas> listaJornadas) {
        for (int i = 0; i < listaJornadas.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaJornadas.borrar(listaJornadas.get(i));
        }
    }

    public void crearJornadas(List<Jornadas> listaJornadas) {
        for (int i = 0; i < listaJornadas.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaJornadas.crear(listaJornadas.get(i));
        }
    }

    @Override
    public List<Jornadas> consultarJornadas() {
        List<Jornadas> listMotivosCambiosCargos;
        listMotivosCambiosCargos = persistenciaJornadas.consultarJornadas();
        return listMotivosCambiosCargos;
    }

    public Jornadas consultarJornada(BigInteger secJornadas) {
        Jornadas subCategoria;
        subCategoria = persistenciaJornadas.consultarJornada(secJornadas);
        return subCategoria;
    }

    public BigInteger contarJornadasLaboralesJornada(BigInteger secJornadas) {
        BigInteger contarJornadasLaboralesJornada = null;

        try {
            return contarJornadasLaboralesJornada = persistenciaJornadas.contarJornadasLaboralesJornada(secJornadas);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarJornadas contarJornadasLaboralesJornada ERROR : " + e);
            return null;
        }
    }

    public BigInteger contarTarifasEscalafonesJornada(BigInteger secJornadas) {
        BigInteger contarTarifasEscalafonesJornada = null;

        try {
            return contarTarifasEscalafonesJornada = persistenciaJornadas.contarTarifasEscalafonesJornada(secJornadas);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarJornadas contarTarifasEscalafonesJornada ERROR : " + e);
            return null;
        }
    }
}
