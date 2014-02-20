/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Operandos;
import InterfaceAdministrar.AdministrarOperandosInterface;
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
public class AdministrarOperandos implements AdministrarOperandosInterface{

    @EJB
    PersistenciaOperandosInterface persistenciaOperandos;

    @Override
    public List<Operandos> buscarOperandos() {
        List<Operandos> listaOperandos;
        listaOperandos = persistenciaOperandos.buscarOperandos();
        return listaOperandos;
    }

    @Override
    public void borrarOperando(Operandos operandos) {
        persistenciaOperandos.borrar(operandos);
    }

    @Override
    public void crearOperando(Operandos operandos) {
        persistenciaOperandos.crear(operandos);
    }

    @Override
    public void modificarOperando(List<Operandos> listaOperandosModificar) {
        for (int i = 0; i < listaOperandosModificar.size(); i++) {
            System.out.println("Modificando...");
            if (listaOperandosModificar.get(i).getValor()== null) {
                listaOperandosModificar.get(i).setValor(null);
            }
            persistenciaOperandos.editar(listaOperandosModificar.get(i));
        }
    }
    
    public String buscarValores(BigInteger secuenciaOperando) {
        String valores;
        valores = persistenciaOperandos.valores(secuenciaOperando);
        return valores;
    }

}
