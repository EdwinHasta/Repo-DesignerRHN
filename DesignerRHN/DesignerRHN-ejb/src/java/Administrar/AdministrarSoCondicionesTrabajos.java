/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarSoCondicionesTrabajosInterface;
import Entidades.SoCondicionesTrabajos;
import InterfacePersistencia.PersistenciaSoCondicionesTrabajosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class AdministrarSoCondicionesTrabajos implements AdministrarSoCondicionesTrabajosInterface {

    @EJB
    PersistenciaSoCondicionesTrabajosInterface persistenciaSoCondicionesTrabajos;

    @Override
    public void modificarSoCondicionesTrabajos(List<SoCondicionesTrabajos> listSoCondicionesTrabajos) {
        for (int i = 0; i < listSoCondicionesTrabajos.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaSoCondicionesTrabajos.editar(listSoCondicionesTrabajos.get(i));
        }
    }
    @Override
    public void borrarSoCondicionesTrabajos(List<SoCondicionesTrabajos> listSoCondicionesTrabajos) {
        for (int i = 0; i < listSoCondicionesTrabajos.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaSoCondicionesTrabajos.borrar(listSoCondicionesTrabajos.get(i));
        }
    }
    @Override
    public void crearSoCondicionesTrabajos(List<SoCondicionesTrabajos> listSoCondicionesTrabajos) {
        for (int i = 0; i < listSoCondicionesTrabajos.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaSoCondicionesTrabajos.crear(listSoCondicionesTrabajos.get(i));
        }
    }
    @Override
    public List<SoCondicionesTrabajos> consultarSoCondicionesTrabajos() {
        List<SoCondicionesTrabajos> listSoCondicionesTrabajos;
        listSoCondicionesTrabajos = persistenciaSoCondicionesTrabajos.buscarSoCondicionesTrabajos();
        return listSoCondicionesTrabajos;
    }
    @Override
    public SoCondicionesTrabajos consultarSoCondicionTrabajo(BigInteger secSoCondicionesTrabajos) {
        SoCondicionesTrabajos soCondicionesTrabajos;
        soCondicionesTrabajos = persistenciaSoCondicionesTrabajos.buscarSoCondicionTrabajo(secSoCondicionesTrabajos);
        return soCondicionesTrabajos;
    }
    @Override
    public BigInteger contarInspeccionesSoCondicionTrabajo(BigInteger secuenciaElementos) {
        try {
            System.err.println("Secuencia Borrado Elementos" + secuenciaElementos);
            BigInteger verificarInspecciones;
            return verificarInspecciones = persistenciaSoCondicionesTrabajos.contadorInspecciones(secuenciaElementos);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarSoCondicionesTrabajos verificarInspecciones ERROR :" + e);
            return null;
        }
    }
    @Override
    public BigInteger contarSoAccidentesMedicosSoCondicionTrabajo(BigInteger secuenciaElementos) {
        try {
            BigInteger verificarSoAccidtenesMedicos;
            System.err.println("Secuencia Borrado Elementos" + secuenciaElementos);
            return verificarSoAccidtenesMedicos = persistenciaSoCondicionesTrabajos.contadorSoAccidentesMedicos(secuenciaElementos);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarSoCondicionesTrabajos verificarSoAccidtenesMedicos ERROR :" + e);
            return null;
        }
    }
    @Override
    public BigInteger contarSoDetallesPanoramasSoCondicionTrabajo(BigInteger secuenciaElementos) {
        try {
            System.err.println("Secuencia Borrado Elementos" + secuenciaElementos);
            BigInteger verificarSoDetallesPanoramas;
            return verificarSoDetallesPanoramas = persistenciaSoCondicionesTrabajos.contadorSoDetallesPanoramas(secuenciaElementos);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarSoCondicionesTrabajos verificarSoDetallesPanoramas ERROR :" + e);
            return null;
        }
    }
    @Override
    public BigInteger contarSoExposicionesFrSoCondicionTrabajo(BigInteger secuenciaElementos) {
        try {
            System.err.println("Secuencia Borrado Elementos" + secuenciaElementos);
            BigInteger verificarSoExposicionesFr;
            return verificarSoExposicionesFr = persistenciaSoCondicionesTrabajos.contadorSoExposicionesFr(secuenciaElementos);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarClasesAccidentes verificarSoExposicionesFr ERROR :" + e);
            return null;
        }
    }

}
