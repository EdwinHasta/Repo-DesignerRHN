/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarPartesCuerpoInterface;
import Entidades.PartesCuerpo;
import InterfacePersistencia.PersistenciaPartesCuerpoInterface;
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

    public void modificarPartesCuerpo(List<PartesCuerpo> listPartesCuerpo) {
        for (int i = 0; i < listPartesCuerpo.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaPartesCuerpo.editar(listPartesCuerpo.get(i));
        }
    }

    public void borrarPartesCuerpo(List<PartesCuerpo> listPartesCuerpo) {
        for (int i = 0; i < listPartesCuerpo.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaPartesCuerpo.borrar(listPartesCuerpo.get(i));
        }
    }

    public void crearPartesCuerpo(List<PartesCuerpo> listPartesCuerpo) {
        for (int i = 0; i < listPartesCuerpo.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaPartesCuerpo.crear(listPartesCuerpo.get(i));
        }
    }

    public List<PartesCuerpo> consultarPartesCuerpo() {
        List<PartesCuerpo> listPartesCuerpo;
        listPartesCuerpo = persistenciaPartesCuerpo.buscarPartesCuerpo();
        return listPartesCuerpo;
    }

    public PartesCuerpo consultarParteCuerpo(BigInteger secElementosCausasAccidentes) {
        PartesCuerpo partesCuerpo;
        partesCuerpo = persistenciaPartesCuerpo.buscarParteCuerpo(secElementosCausasAccidentes);
        return partesCuerpo;
    }

    public BigInteger contarSoAccidentesMedicosParteCuerpo(BigInteger secuenciaElementosCausasAccidentes) {
        try {
            BigInteger verificarSoAccidentesMedicos;

            return verificarSoAccidentesMedicos = persistenciaPartesCuerpo.contadorSoAccidentesMedicos(secuenciaElementosCausasAccidentes);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARPARTESCUERPO verificarBorradoDetallesLicensias ERROR :" + e);
            return null;
        }
    }

    public BigInteger contarDetallesExamenesParteCuerpo(BigInteger secuenciaElementosCausasAccidentes) {
        try {
            BigInteger verificarBorradoDetallesExamenes;
            return verificarBorradoDetallesExamenes = persistenciaPartesCuerpo.contadorDetallesExamenes(secuenciaElementosCausasAccidentes);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARPARTESCUERPO verificarBorradoSoAccidentesDomesticos ERROR :" + e);
            return null;
        }
    }

    public BigInteger contarSoDetallesRevisionesParteCuerpo(BigInteger secuenciaElementosCausasAccidentes) {
        try {
            BigInteger verificarBorradoSoDetallesRevisiones;
            return verificarBorradoSoDetallesRevisiones = persistenciaPartesCuerpo.contadorSoDetallesRevisiones(secuenciaElementosCausasAccidentes);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARPARTESCUERPO verificarBorradoSoAccidentesDomesticos ERROR :" + e);
            return null;
        }
    }
}
