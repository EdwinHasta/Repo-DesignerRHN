/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.MotivosRetiros;
import InterfaceAdministrar.AdministrarMotivosRetirosInterface;
import InterfacePersistencia.PersistenciaMotivosRetirosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarMotivosRetiros implements AdministrarMotivosRetirosInterface {

    @EJB
    PersistenciaMotivosRetirosInterface persistenciaMotivosRetiros;

    @Override
    public void modificarMotivosRetiros(List<MotivosRetiros> listaMotivosRetiros) {
        for (int i = 0; i < listaMotivosRetiros.size(); i++) {
            System.out.println("Administrar Modificando...");
            System.out.println("Nombre " + listaMotivosRetiros.get(i).getNombre() + " Codigo " + listaMotivosRetiros.get(i).getCodigo());
            persistenciaMotivosRetiros.editar(listaMotivosRetiros.get(i));
        }
    }

    @Override
    public void borrarMotivosRetiros(List<MotivosRetiros> listaMotivosRetiros) {
        for (int i = 0; i < listaMotivosRetiros.size(); i++) {
            System.out.println("Administrar Borrando...");
            System.out.println("Nombre " + listaMotivosRetiros.get(i).getNombre() + " Codigo " + listaMotivosRetiros.get(i).getCodigo());
            persistenciaMotivosRetiros.borrar(listaMotivosRetiros.get(i));
        }
    }

    @Override
    public void crearMotivosRetiros(List<MotivosRetiros> listaMotivosRetiros) {
        for (int i = 0; i < listaMotivosRetiros.size(); i++) {
            System.out.println("Administrar Creando...");
            System.out.println("Nombre " + listaMotivosRetiros.get(i).getNombre() + " Codigo " + listaMotivosRetiros.get(i).getCodigo());
            persistenciaMotivosRetiros.crear(listaMotivosRetiros.get(i));
        }
    }

    public List<MotivosRetiros> consultarMotivosRetiros() {
        List<MotivosRetiros> listMotivosCambiosCargos;
        listMotivosCambiosCargos = persistenciaMotivosRetiros.consultarMotivosRetiros();
        return listMotivosCambiosCargos;
    }

    @Override
    public MotivosRetiros consultarMotivoRetiro(BigInteger secMotivosRetiros) {
        MotivosRetiros subCategoria;
        subCategoria = persistenciaMotivosRetiros.consultarMotivoRetiro(secMotivosRetiros);
        return subCategoria;
    }

    @Override
    public BigInteger contarHVExperienciasLaboralesMotivoRetiro(BigInteger secMotivosRetiros) {
        BigInteger contarHVExperienciasLaboralesMotivoRetiro = null;

        try {
            return contarHVExperienciasLaboralesMotivoRetiro = persistenciaMotivosRetiros.contarHVExperienciasLaboralesMotivoRetiro(secMotivosRetiros);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarMotivosRetiros contarEscalafones ERROR : " + e);
            return null;
        }
    }

    @Override
    public BigInteger contarNovedadesSistemasMotivoRetiro(BigInteger secMotivosRetiros) {
        BigInteger contarNovedadesSistemasMotivoRetiro = null;

        try {
            return contarNovedadesSistemasMotivoRetiro = persistenciaMotivosRetiros.contarNovedadesSistemasMotivoRetiro(secMotivosRetiros);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarMotivosRetiros contarEscalafones ERROR : " + e);
            return null;
        }
    }

    @Override
    public BigInteger contarRetiMotivosRetirosMotivoRetiro(BigInteger secMotivosRetiros) {
        BigInteger contarRetiMotivosRetirosMotivoRetiro = null;

        try {
            return contarRetiMotivosRetirosMotivoRetiro = persistenciaMotivosRetiros.contarRetiMotivosRetirosMotivoRetiro(secMotivosRetiros);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarMotivosRetiros contarEscalafones ERROR : " + e);
            return null;
        }
    }

    @Override
    public BigInteger contarRetiradosMotivoRetiro(BigInteger secMotivosRetiros) {
        BigInteger contarRetiradosMotivoRetiro = null;

        try {
            return contarRetiradosMotivoRetiro = persistenciaMotivosRetiros.contarRetiradosMotivoRetiro(secMotivosRetiros);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarMotivosRetiros contarRetiradosMotivoRetiro ERROR : " + e);
            return null;
        }
    }
}
