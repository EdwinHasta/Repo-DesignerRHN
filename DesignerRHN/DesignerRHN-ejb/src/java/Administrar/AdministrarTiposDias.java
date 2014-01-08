/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Administrar;

import InterfaceAdministrar.AdministrarTiposDiasInterface;
import Entidades.TiposDias;
import InterfacePersistencia.PersistenciaTiposDiasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarTiposDias implements AdministrarTiposDiasInterface {

   @EJB
    PersistenciaTiposDiasInterface persistenciaTiposDias;
    private TiposDias tipoDiaSeleccionado;
    private TiposDias tiposDias;
    private List<TiposDias> listTiposDias;

    public void modificarTiposDias(List<TiposDias> listaTiposDiasModificados) {
        for (int i = 0; i < listaTiposDiasModificados.size(); i++) {
            System.out.println("Administrar Modificando...");
            tipoDiaSeleccionado = listaTiposDiasModificados.get(i);
            persistenciaTiposDias.editar(tipoDiaSeleccionado);
        }
    }

    public void borrarTiposDias(TiposDias tiposDias) {
        persistenciaTiposDias.borrar(tiposDias);
    }

    public void crearTiposDias(TiposDias tiposDias) {
        persistenciaTiposDias.crear(tiposDias);
    }

    public List<TiposDias> mostrarTiposDias() {
        listTiposDias = persistenciaTiposDias.buscarTiposDias();
        return listTiposDias;
    }

    public TiposDias mostrarTipoDia(BigInteger secTipoDia) {
        tiposDias = persistenciaTiposDias.buscarTipoDia(secTipoDia);
        return tiposDias;
    }

    public BigInteger verificarDiasLaborales(BigInteger secuenciaTiposDias) {
        BigInteger verificarBorradoDiasLaborales = null;
        try {
            verificarBorradoDiasLaborales = persistenciaTiposDias.contadorDiasLaborales(secuenciaTiposDias);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARTIPOSDIAS VERIFICARDIASLABORALES ERROR :" + e);
        } finally {
            return verificarBorradoDiasLaborales;
        }
    }
    public BigInteger verificarExtrasRecargos(BigInteger secuenciaTiposDias) {
        BigInteger verificarBorradoExtrasRecargos = null;
        try {
            verificarBorradoExtrasRecargos = persistenciaTiposDias.contadorExtrasRecargos(secuenciaTiposDias);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARTIPOSDIAS VERIFICAREXTRASRECARGOS ERROR :" + e);
        } finally {
            return verificarBorradoExtrasRecargos;
        }
    }
}
