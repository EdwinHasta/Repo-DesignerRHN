/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarPartesCuerpoInterface;
import Entidades.PartesCuerpo;
import InterfacePersistencia.PersistenciaPartesCuerpoInterface;
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
public class AdministrarPartesCuerpo implements AdministrarPartesCuerpoInterface {

    @EJB
    PersistenciaPartesCuerpoInterface persistenciaPartesCuerpo;
    private PartesCuerpo partesCuerpoSeleccionada;
    private PartesCuerpo partesCuerpo;
    private List<PartesCuerpo> listPartesCuerpo;
    private BigDecimal verificarSoAccidentesMedicos;
    private BigDecimal verificarBorradoDetallesExamenes;
    private BigDecimal verificarBorradoSoDetallesRevisiones;

    public void modificarPartesCuerpo(List<PartesCuerpo> listPartesCuerpoModificada) {
        for (int i = 0; i < listPartesCuerpoModificada.size(); i++) {
            System.out.println("Administrar Modificando...");
            partesCuerpoSeleccionada = listPartesCuerpoModificada.get(i);
            persistenciaPartesCuerpo.editar(partesCuerpoSeleccionada);
        }
    }

    public void borrarPartesCuerpo(PartesCuerpo elementosCausasAccidentes) {
        persistenciaPartesCuerpo.borrar(elementosCausasAccidentes);
    }

    public void crearPartesCuerpo(PartesCuerpo elementosCausasAccidentes) {
        persistenciaPartesCuerpo.crear(elementosCausasAccidentes);
    }

    public List<PartesCuerpo> mostrarPartesCuerpo() {
        listPartesCuerpo = persistenciaPartesCuerpo.buscarPartesCuerpo();
        return listPartesCuerpo;
    }

    public PartesCuerpo mostrarParteCuerpo(BigInteger secElementosCausasAccidentes) {
        partesCuerpo = persistenciaPartesCuerpo.buscarParteCuerpo(secElementosCausasAccidentes);
        return partesCuerpo;
    }

    public BigDecimal verificarSoAccidentesMedicos(BigInteger secuenciaElementosCausasAccidentes) {
        try {
            verificarSoAccidentesMedicos = persistenciaPartesCuerpo.contadorSoAccidentesMedicos(secuenciaElementosCausasAccidentes);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARPARTESCUERPO verificarBorradoDetallesLicensias ERROR :" + e);
        } finally {
            return verificarSoAccidentesMedicos;
        }
    }

    public BigDecimal verificarBorradoDetallesExamenes(BigInteger secuenciaElementosCausasAccidentes) {
        try {
            verificarBorradoDetallesExamenes = persistenciaPartesCuerpo.contadorDetallesExamenes(secuenciaElementosCausasAccidentes);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARPARTESCUERPO verificarBorradoSoAccidentesDomesticos ERROR :" + e);
        } finally {
            return verificarBorradoDetallesExamenes;
        }
    }

    public BigDecimal verificarBorradoSoDetallesRevisiones(BigInteger secuenciaElementosCausasAccidentes) {
        try {
            verificarBorradoSoDetallesRevisiones = persistenciaPartesCuerpo.contadorSoDetallesRevisiones(secuenciaElementosCausasAccidentes);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARPARTESCUERPO verificarBorradoSoAccidentesDomesticos ERROR :" + e);
        } finally {
            return verificarBorradoSoDetallesRevisiones;
        }
    }
}
