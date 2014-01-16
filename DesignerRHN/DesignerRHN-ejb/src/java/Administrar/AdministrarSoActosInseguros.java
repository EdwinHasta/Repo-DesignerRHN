/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarSoActosInsegurosInterface;
import Entidades.SoActosInseguros;
import InterfacePersistencia.PersistenciaSoActosInsegurosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author John Pineda
 */
@Stateful
public class AdministrarSoActosInseguros implements AdministrarSoActosInsegurosInterface {

    @EJB
    PersistenciaSoActosInsegurosInterface persistenciaSoActosInseguros;

    private List<SoActosInseguros> listSoActosInseguros;

    @Override
    public void modificarSoActosInseguros(List<SoActosInseguros> listSoActosInseguros) {
        for (int i = 0; i < listSoActosInseguros.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaSoActosInseguros.editar(listSoActosInseguros.get(i));
        }
    }

    @Override
    public void borrarSoActosInseguros(List<SoActosInseguros> listSoActosInseguros) {
        for (int i = 0; i < listSoActosInseguros.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaSoActosInseguros.borrar(listSoActosInseguros.get(i));
        }
    }

    @Override
    public void crearSoActosInseguros(List<SoActosInseguros> listSoActosInseguros) {
        for (int i = 0; i < listSoActosInseguros.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaSoActosInseguros.crear(listSoActosInseguros.get(i));
        }
    }

    @Override
    public List<SoActosInseguros> consultarSoActosInseguros() {
        listSoActosInseguros = persistenciaSoActosInseguros.buscarSoActosInseguros();
        return listSoActosInseguros;
    }

    @Override
    public SoActosInseguros consultarSoActoInseguro(BigInteger secSoCondicionesAmbientalesP) {
        SoActosInseguros soActosInseguros;
        soActosInseguros = persistenciaSoActosInseguros.buscarSoActoInseguro(secSoCondicionesAmbientalesP);
        return soActosInseguros;
    }

    @Override
    public BigInteger verificarSoAccidentesMedicos(BigInteger secuenciaElementos) {
        BigInteger verificarSoAccidtenesMedicos;
        try {
            System.err.println("Secuencia Borrado Elementos" + secuenciaElementos);
            return verificarSoAccidtenesMedicos = persistenciaSoActosInseguros.contadorSoAccidentesMedicos(secuenciaElementos);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARSOACTOSINSEGUROS verificarSoAccidtenesMedicos ERROR :" + e);
            return null;
        }
    }
}
