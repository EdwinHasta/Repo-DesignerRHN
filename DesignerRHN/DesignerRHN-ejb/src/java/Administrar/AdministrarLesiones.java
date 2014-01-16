/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarLesionesInterface;
import Entidades.Lesiones;
import InterfacePersistencia.PersistenciaLesionesInterface;
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

    @Override
    public void modificarLesiones(List<Lesiones> listaLesiones) {
        for (int i = 0; i < listaLesiones.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaLesiones.editar(listaLesiones.get(i));
        }
    }

    @Override
    public void borrarLesiones(List<Lesiones> listaLesiones) {
        for (int i = 0; i < listaLesiones.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaLesiones.borrar(listaLesiones.get(i));
        }
    }

    @Override
    public void crearLesiones(List<Lesiones> listaLesiones) {
        for (int i = 0; i < listaLesiones.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaLesiones.crear(listaLesiones.get(i));
        }
    }

    @Override
    public List<Lesiones> consultarLesiones() {
        List<Lesiones> listLesiones;
        listLesiones = persistenciaLesiones.buscarLesiones();
        return listLesiones;
    }

    @Override
    public Lesiones consultarLesion(BigInteger secLesion) {
        Lesiones lesiones;
        lesiones = persistenciaLesiones.buscarLesion(secLesion);
        return lesiones;
    }

    @Override
    public BigInteger contarDetallesLicensiasLesion(BigInteger secuenciaLesiones) {
        try {
            BigInteger verificarBorradoDetallesLicensias;
            return verificarBorradoDetallesLicensias = persistenciaLesiones.contadorDetallesLicensias(secuenciaLesiones);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarLesiones verificarBorradoDetallesLicensias ERROR :" + e);
            return null;
        }
    }

    @Override
    public BigInteger contarSoAccidentesDomesticosLesion(BigInteger secuenciaVigenciasExamenesMedicos) {
        try {
            BigInteger verificadorVigenciasExamenesMedicos;
            return verificadorVigenciasExamenesMedicos = persistenciaLesiones.contadorSoAccidentesDomesticos(secuenciaVigenciasExamenesMedicos);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarLesiones verificarBorradoSoAccidentesDomesticos ERROR :" + e);
            return null;
        }
    }
}
