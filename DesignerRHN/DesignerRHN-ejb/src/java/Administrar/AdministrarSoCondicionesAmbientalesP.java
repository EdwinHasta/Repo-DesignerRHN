/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Administrar;

import InterfaceAdministrar.AdministrarSoCondicionesAmbientalesPInterface;
import Entidades.SoCondicionesAmbientalesP;
import InterfacePersistencia.PersistenciaSoCondicionesAmbientalesPInterface;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarSoCondicionesAmbientalesP implements AdministrarSoCondicionesAmbientalesPInterface {

    @EJB
    PersistenciaSoCondicionesAmbientalesPInterface persistenciaSoCondicionesAmbientalesP;
    private SoCondicionesAmbientalesP soCondicionesAmbientalesPSeleccionada;
    private SoCondicionesAmbientalesP soCondicionesAmbientalesP;
    private List<SoCondicionesAmbientalesP> listSoCondicionesAmbientalesP;
    private BigDecimal verificarSoAccidtenesMedicos;

    public void modificarSoCondicionesAmbientalesP(List<SoCondicionesAmbientalesP> listSoCondicionesAmbientalesPModificada) {
        for (int i = 0; i < listSoCondicionesAmbientalesPModificada.size(); i++) {
            System.out.println("Administrar Modificando...");
            soCondicionesAmbientalesPSeleccionada = listSoCondicionesAmbientalesPModificada.get(i);
            persistenciaSoCondicionesAmbientalesP.editar(soCondicionesAmbientalesPSeleccionada);
        }
    }

    public void borrarSoCondicionesAmbientalesP(SoCondicionesAmbientalesP soCondicionesAmbientalesP) {
        persistenciaSoCondicionesAmbientalesP.borrar(soCondicionesAmbientalesP);
    }

    public void crearSoCondicionesAmbientalesP(SoCondicionesAmbientalesP soCondicionesAmbientalesP) {
        persistenciaSoCondicionesAmbientalesP.crear(soCondicionesAmbientalesP);
    }

    public List<SoCondicionesAmbientalesP> mostrarSoCondicionesAmbientalesP() {
        listSoCondicionesAmbientalesP = persistenciaSoCondicionesAmbientalesP.buscarSoCondicionesAmbientalesP();
        return listSoCondicionesAmbientalesP;
    }

    public SoCondicionesAmbientalesP mostrarSoCondicionAmbientalP(BigInteger secSoCondicionesAmbientalesP) {
        soCondicionesAmbientalesP = persistenciaSoCondicionesAmbientalesP.buscarSoCondicionAmbientalP(secSoCondicionesAmbientalesP);
        return soCondicionesAmbientalesP;
    }

    public BigDecimal verificarSoAccidentesMedicos(BigInteger secuenciaElementos) {
        try {
            System.err.println("Secuencia Borrado Elementos" + secuenciaElementos);
            verificarSoAccidtenesMedicos = persistenciaSoCondicionesAmbientalesP.contadorSoAccidentesMedicos(secuenciaElementos);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARSOCONDICIONSEAMBIENTALESP verificarSoAccidtenesMedicos ERROR :" + e);
        } finally {
            return verificarSoAccidtenesMedicos;
        }
    }
}
