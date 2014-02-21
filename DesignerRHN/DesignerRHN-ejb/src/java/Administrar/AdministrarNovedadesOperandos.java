/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Formulas;
import Entidades.NovedadesOperandos;
import Entidades.Operandos;
import InterfaceAdministrar.AdministrarNovedadesOperandosInterface;
import InterfacePersistencia.PersistenciaFormulasInterface;
import InterfacePersistencia.PersistenciaNovedadesOperandosInterface;
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
public class AdministrarNovedadesOperandos implements AdministrarNovedadesOperandosInterface {

    @EJB
    PersistenciaNovedadesOperandosInterface persistenciaNovedadesOperandos;
    @EJB
    PersistenciaOperandosInterface persistenciaOperandos;
    @EJB
    PersistenciaFormulasInterface persistenciaFormulas;

    @Override
    public List<NovedadesOperandos> buscarNovedadesOperandos(BigInteger secuenciaOperando) {
        List<NovedadesOperandos> listaNovedadesOperandos;
        listaNovedadesOperandos = persistenciaNovedadesOperandos.novedadesOperandos(secuenciaOperando);
        return listaNovedadesOperandos;
    }

    @Override
    public void borrarNovedadesOperandos(NovedadesOperandos novedadesOperandos) {
        persistenciaNovedadesOperandos.borrar(novedadesOperandos);
    }

    @Override
    public void crearNovedadesOperandos(NovedadesOperandos novedadesOperandos) {
        persistenciaNovedadesOperandos.crear(novedadesOperandos);
    }

    @Override
    public void modificarNovedadesOperandos(NovedadesOperandos novedadesOperandos) {
        persistenciaNovedadesOperandos.editar(novedadesOperandos);

    }

    @Override
    public List<Operandos> buscarOperandos() {
        List<Operandos> listaOperandos;
        listaOperandos = persistenciaOperandos.buscarOperandos();
        return listaOperandos;
    }

}
