/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Administrar;

import InterfaceAdministrar.AdministrarLesionesInterface;
import Entidades.Lesiones;
import InterfacePersistencia.PersistenciaLesionesInterface;
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
public class AdministrarLesiones implements AdministrarLesionesInterface {

 @EJB
    PersistenciaLesionesInterface persistenciaLesiones;
    private Lesiones lesionSeleccionada;
    private Lesiones lesiones;
    private List<Lesiones> listLesiones;
    private BigInteger verificarBorradoDetallesLicensias;
    private BigInteger verificadorVigenciasExamenesMedicos;

    public void modificarLesiones(List<Lesiones> listLesionesModificadas) {
        for (int i = 0; i < listLesionesModificadas.size(); i++) {
            System.out.println("Administrar Modificando...");
            lesionSeleccionada = listLesionesModificadas.get(i);
            persistenciaLesiones.editar(lesionSeleccionada);
        }
    }

    public void borrarLesiones(Lesiones lesiones) {
        persistenciaLesiones.borrar(lesiones);
    }

    public void crearLesiones(Lesiones lesiones) {
        persistenciaLesiones.crear(lesiones);
    }

    public List<Lesiones> mostrarLesiones() {
        listLesiones = persistenciaLesiones.buscarLesiones();
        return listLesiones;
    }

    public Lesiones mostrarLesion(BigInteger secLesion) {
        lesiones = persistenciaLesiones.buscarLesion(secLesion);
        return lesiones;
    }

    public BigInteger verificarBorradoDetallesLicensias(BigInteger secuenciaLesiones) {
        try {
            verificarBorradoDetallesLicensias = persistenciaLesiones.contadorDetallesLicensias(secuenciaLesiones);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarLesiones verificarBorradoDetallesLicensias ERROR :" + e);
        } finally {
            return verificarBorradoDetallesLicensias;
        }
    }

    public BigInteger verificarBorradoSoAccidentesDomesticos(BigInteger secuenciaVigenciasExamenesMedicos) {
        try {
            verificadorVigenciasExamenesMedicos = persistenciaLesiones.contadorSoAccidentesDomesticos(secuenciaVigenciasExamenesMedicos);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarLesiones verificarBorradoSoAccidentesDomesticos ERROR :" + e);
        } finally {
            return verificadorVigenciasExamenesMedicos;
        }
    }
}
