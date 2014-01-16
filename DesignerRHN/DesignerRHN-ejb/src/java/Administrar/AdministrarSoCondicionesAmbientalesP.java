/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarSoCondicionesAmbientalesPInterface;
import Entidades.SoCondicionesAmbientalesP;
import InterfacePersistencia.PersistenciaSoCondicionesAmbientalesPInterface;
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

    @Override
    public void modificarSoCondicionesAmbientalesP(List<SoCondicionesAmbientalesP> listSoCondicionesAmbientalesP) {
        for (int i = 0; i < listSoCondicionesAmbientalesP.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaSoCondicionesAmbientalesP.editar(listSoCondicionesAmbientalesP.get(i));
        }
    }

    @Override
    public void borrarSoCondicionesAmbientalesP(List<SoCondicionesAmbientalesP> listSoCondicionesAmbientalesP) {
        for (int i = 0; i < listSoCondicionesAmbientalesP.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaSoCondicionesAmbientalesP.borrar(listSoCondicionesAmbientalesP.get(i));
        }
    }

    @Override
    public void crearSoCondicionesAmbientalesP(List<SoCondicionesAmbientalesP> listSoCondicionesAmbientalesP) {
        for (int i = 0; i < listSoCondicionesAmbientalesP.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaSoCondicionesAmbientalesP.crear(listSoCondicionesAmbientalesP.get(i));
        }
    }

    @Override
    public List<SoCondicionesAmbientalesP> consultarSoCondicionesAmbientalesP() {
        List<SoCondicionesAmbientalesP> listSoCondicionesAmbientalesP;
        listSoCondicionesAmbientalesP = persistenciaSoCondicionesAmbientalesP.buscarSoCondicionesAmbientalesP();
        return listSoCondicionesAmbientalesP;
    }

    @Override
    public SoCondicionesAmbientalesP consultarSoCondicionAmbientalP(BigInteger secSoCondicionesAmbientalesP) {
        SoCondicionesAmbientalesP soCondicionesAmbientalesP;
        soCondicionesAmbientalesP = persistenciaSoCondicionesAmbientalesP.buscarSoCondicionAmbientalP(secSoCondicionesAmbientalesP);
        return soCondicionesAmbientalesP;
    }

    @Override
    public BigInteger verificarSoAccidentesMedicos(BigInteger secuenciaElementos) {
        BigInteger verificarSoAccidtenesMedicos;
        try {
            System.err.println("Secuencia Borrado Elementos" + secuenciaElementos);
            return verificarSoAccidtenesMedicos = persistenciaSoCondicionesAmbientalesP.contadorSoAccidentesMedicos(secuenciaElementos);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARSOCONDICIONSEAMBIENTALESP verificarSoAccidtenesMedicos ERROR :" + e);
            return null;
        }
    }
}
