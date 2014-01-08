/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.GruposViaticos;
import InterfaceAdministrar.AdministrarGruposViaticosInterface;
import InterfacePersistencia.PersistenciaGruposViaticosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class AdministrarGruposViaticos implements AdministrarGruposViaticosInterface {

    @EJB
    PersistenciaGruposViaticosInterface persistenciaGruposViaticos;
    private GruposViaticos gruposViaticosSeleccionada;
    private GruposViaticos evalGruposViaticos;
    private List<GruposViaticos> listGruposViaticos;

    public void modificarGruposViaticos(List<GruposViaticos> listGruposViativosModificadas) {
        for (int i = 0; i < listGruposViativosModificadas.size(); i++) {
            System.out.println("Administrar Modificando...");
            gruposViaticosSeleccionada = listGruposViativosModificadas.get(i);
            persistenciaGruposViaticos.editar(gruposViaticosSeleccionada);
        }
    }

    public void borrarGruposViaticos(GruposViaticos gruposViaticos) {
        persistenciaGruposViaticos.borrar(gruposViaticos);
    }

    public void crearGruposViaticos(GruposViaticos gruposViaticos) {
        persistenciaGruposViaticos.crear(gruposViaticos);
    }

    @Override
    public List<GruposViaticos> mostrarGruposViaticos() {
        listGruposViaticos = persistenciaGruposViaticos.buscarGruposViaticos();
        return listGruposViaticos;
    }

    public GruposViaticos mostrarGrupoViatico(BigInteger secGruposViaticos) {
        evalGruposViaticos = persistenciaGruposViaticos.buscarGrupoViatico(secGruposViaticos);
        return evalGruposViaticos;
    }

    public BigInteger verificarBorradoCargos(BigInteger secuenciaCargos) {
        BigInteger verificadorCargos = null;
        try {
            System.err.println("Secuencia Borrado  Cargos" + secuenciaCargos);
            verificadorCargos = persistenciaGruposViaticos.contadorCargos(secuenciaCargos);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarGruposViativos verificarBorradoCargos ERROR :" + e);
        } finally {
            return verificadorCargos;
        }
    }

    public BigInteger verificarBorradoPlantas(BigInteger secuenciaCargos) {
        BigInteger verificadorPlantas = null;
        try {
            System.err.println("Secuencia Borrado  Plantas" + secuenciaCargos);
            verificadorPlantas = persistenciaGruposViaticos.contadorPlantas(secuenciaCargos);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarGruposViativos verificarBorradoPlantas ERROR :" + e);
        } finally {
            return verificadorPlantas;
        }
    }

    public BigInteger verificarTablasViaticos(BigInteger secuenciaCargos) {
        BigInteger verificadorTablasViaticos = null;
        try {
            System.err.println("Secuencia Borrado  Tablas Viaticos" + secuenciaCargos);
            verificadorTablasViaticos = persistenciaGruposViaticos.contadorTablasViaticos(secuenciaCargos);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarGruposViativos verificarTablasViaticos ERROR :" + e);
        } finally {
            return verificadorTablasViaticos;
        }
    }

    public BigInteger verificarEersViaticos(BigInteger secuenciaCargos) {
        BigInteger verificadorErsViaticos = null;
        try {
            System.err.println("Secuencia Borrado  Tablas ErsViaticos" + secuenciaCargos);
            verificadorErsViaticos = persistenciaGruposViaticos.contadorEersviaticos(secuenciaCargos);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarGruposViativos verificarEersViaticos ERROR :" + e);
        } finally {
            return verificadorErsViaticos;
        }
    }
}
