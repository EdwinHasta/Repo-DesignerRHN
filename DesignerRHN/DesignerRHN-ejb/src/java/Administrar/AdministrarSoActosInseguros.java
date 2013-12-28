/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Administrar;

import InterfaceAdministrar.AdministrarSoActosInsegurosInterface;
import Entidades.SoActosInseguros;
import InterfacePersistencia.PersistenciaSoActosInsegurosInterface;
import java.math.BigDecimal;
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
    private SoActosInseguros soActosInsegurosPSeleccionada;
    private SoActosInseguros soActosInseguros;
    private List<SoActosInseguros> listSoActosInseguros;
    private BigDecimal verificarSoAccidtenesMedicos;

    public void modificarSoActosInseguros(List<SoActosInseguros> listSoActosInsegurosPModificada) {
        for (int i = 0; i < listSoActosInsegurosPModificada.size(); i++) {
            System.out.println("Administrar Modificando...");
            soActosInsegurosPSeleccionada = listSoActosInsegurosPModificada.get(i);
            persistenciaSoActosInseguros.editar(soActosInsegurosPSeleccionada);
        }
    }

    public void borrarSoActosInseguros(SoActosInseguros soActosInseguros) {
        persistenciaSoActosInseguros.borrar(soActosInseguros);
    }

    public void crearSoActosInseguros(SoActosInseguros soActosInseguros) {
        persistenciaSoActosInseguros.crear(soActosInseguros);
    }

    public List<SoActosInseguros> mostrarSoActosInseguros() {
        listSoActosInseguros = persistenciaSoActosInseguros.buscarSoActosInseguros();
        return listSoActosInseguros;
    }

    public SoActosInseguros mostrarSoActoInseguro(BigInteger secSoCondicionesAmbientalesP) {
        soActosInseguros = persistenciaSoActosInseguros.buscarSoActoInseguro(secSoCondicionesAmbientalesP);
        return soActosInseguros;
    }

    public BigDecimal verificarSoAccidentesMedicos(BigInteger secuenciaElementos) {
        try {
            System.err.println("Secuencia Borrado Elementos" + secuenciaElementos);
            verificarSoAccidtenesMedicos = persistenciaSoActosInseguros.contadorSoAccidentesMedicos(secuenciaElementos);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARSOACTOSINSEGUROS verificarSoAccidtenesMedicos ERROR :" + e);
        } finally {
            return verificarSoAccidtenesMedicos;
        }
    }
}
