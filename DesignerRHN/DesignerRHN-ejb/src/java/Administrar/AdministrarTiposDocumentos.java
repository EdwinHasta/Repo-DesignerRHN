/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarTiposDocumentosInterface;
import Entidades.TiposDocumentos;
import InterfacePersistencia.PersistenciaTiposDocumentosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarTiposDocumentos implements AdministrarTiposDocumentosInterface {

    @EJB
    PersistenciaTiposDocumentosInterface persistenciaTiposDocumentos;

    @Override
    public void modificarTiposDocumentos(List<TiposDocumentos> listaTiposDocumentos) {
        for (int i = 0; i < listaTiposDocumentos.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaTiposDocumentos.editar(listaTiposDocumentos.get(i));
        }
    }

    @Override
    public void borrarTiposDocumentos(List<TiposDocumentos> listaTiposDocumentos) {
        for (int i = 0; i < listaTiposDocumentos.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaTiposDocumentos.borrar(listaTiposDocumentos.get(i));
        }
    }

    @Override
    public void crearTiposDocumentos(List<TiposDocumentos> listaTiposDocumentos) {
        for (int i = 0; i < listaTiposDocumentos.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaTiposDocumentos.crear(listaTiposDocumentos.get(i));
        }
    }

    public List<TiposDocumentos> consultarTiposDocumentos() {
        List<TiposDocumentos> listMotivosCambiosCargos;
        listMotivosCambiosCargos = persistenciaTiposDocumentos.consultarTiposDocumentos();
        return listMotivosCambiosCargos;
    }

    @Override
    public TiposDocumentos consultarTipoDocumento(BigInteger secTiposDocumentos) {
        TiposDocumentos subCategoria;
        subCategoria = persistenciaTiposDocumentos.consultarTipoDocumento(secTiposDocumentos);
        return subCategoria;
    }

    @Override
    public BigInteger contarCodeudoresTipoDocumento(BigInteger secTiposDocumentos) {
        BigInteger contarRetiradosTipoDocumento = null;

        try {
            return contarRetiradosTipoDocumento = persistenciaTiposDocumentos.contarCodeudoresTipoDocumento(secTiposDocumentos);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarTiposDocumentos contarCodeudoresTipoDocumento ERROR : " + e);
            return null;
        }
    }

    @Override
    public BigInteger contarPersonasTipoDocumento(BigInteger secTiposDocumentos) {
        BigInteger contarPersonasTipoDocumento = null;

        try {
            return contarPersonasTipoDocumento = persistenciaTiposDocumentos.contarPersonasTipoDocumento(secTiposDocumentos);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarTiposDocumentos contarPersonasTipoDocumento ERROR : " + e);
            return null;
        }
    }
}
