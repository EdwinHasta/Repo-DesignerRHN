/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.DependenciasOperandos;
import Entidades.Operandos;
import InterfaceAdministrar.AdministrarDependenciasOperandosInterface;
import InterfacePersistencia.PersistenciaDependenciasOperandosInterface;
import InterfacePersistencia.PersistenciaOperandosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarDependenciasOperandos implements AdministrarDependenciasOperandosInterface {

    @EJB
    PersistenciaDependenciasOperandosInterface persistenciaDependenciasOperandos;
    @EJB
    PersistenciaOperandosInterface persistenciaOperandos;

    @Override
    public List<DependenciasOperandos> buscarDependenciasOperandos(BigInteger secuenciaOperando) {
        List<DependenciasOperandos> listaDependenciasOperandos;
        listaDependenciasOperandos = persistenciaDependenciasOperandos.dependenciasOperandos(secuenciaOperando);
        return listaDependenciasOperandos;
    }

    @Override
    public void borrarDependenciasOperandos(DependenciasOperandos tiposConstantes) {
        persistenciaDependenciasOperandos.borrar(tiposConstantes);
    }

    @Override
    public void crearDependenciasOperandos(DependenciasOperandos tiposConstantes) {
        persistenciaDependenciasOperandos.crear(tiposConstantes);
    }

    @Override
    public void modificarDependenciasOperandos(DependenciasOperandos tiposConstantes) {
        persistenciaDependenciasOperandos.editar(tiposConstantes);

    }

    @Override
    public List<Operandos> buscarOperandos() {
        List<Operandos> listaOperandos;
        listaOperandos = persistenciaOperandos.buscarOperandos();
        return listaOperandos;
    }

    @Override
    public String nombreOperandos(int codigo) {
        String nombre;
        nombre = persistenciaDependenciasOperandos.nombreOperandos(codigo);
        return nombre;
    }
}
