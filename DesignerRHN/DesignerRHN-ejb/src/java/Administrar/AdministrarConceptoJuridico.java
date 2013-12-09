/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.ConceptosJuridicos;
import Entidades.Empresas;
import InterfaceAdministrar.AdministrarConceptoJuridicoInterface;
import InterfacePersistencia.PersistenciaConceptosJuridicosInterface;
import InterfacePersistencia.PersistenciaEmpresasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class AdministrarConceptoJuridico implements AdministrarConceptoJuridicoInterface {

    @EJB
    PersistenciaConceptosJuridicosInterface persistenciaConceptosJuridicos;
    @EJB
    PersistenciaEmpresasInterface persistenciaEmpresas;

    @Override
    public List<ConceptosJuridicos> listConceptosJuridicosPorEmpresa(BigInteger secuencia) {
        try {
            List<ConceptosJuridicos> conceptos = persistenciaConceptosJuridicos.buscarConceptosJuridicosEmpresa(secuencia);
            return conceptos;
        } catch (Exception e) {
            System.out.println("Error listConceptosJuridicosPorEmpresa Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearConceptosJuridicos(List<ConceptosJuridicos> listCJ) {
        try {
            for (int i = 0; i < listCJ.size(); i++) {
                String aux1, aux2, aux3;
                aux1 = listCJ.get(i).getExpedidopor().toUpperCase();
                listCJ.get(i).setExpedidopor(aux1);
                aux2 = listCJ.get(i).getQuien().toUpperCase();
                listCJ.get(i).setQuien(aux2);
                aux3 = listCJ.get(i).getTexto().toUpperCase();
                listCJ.get(i).setTexto(aux3);
                persistenciaConceptosJuridicos.crear(listCJ.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearConceptosJuridicos Admi : " + e.toString());
        }
    }

    @Override
    public void editarConceptosJuridicos(List<ConceptosJuridicos> listCJ) {
        try {
            for (int i = 0; i < listCJ.size(); i++) {
                String aux1, aux2, aux3;
                aux1 = listCJ.get(i).getExpedidopor().toUpperCase();
                listCJ.get(i).setExpedidopor(aux1);
                aux2 = listCJ.get(i).getQuien().toUpperCase();
                listCJ.get(i).setQuien(aux2);
                aux3 = listCJ.get(i).getTexto().toUpperCase();
                listCJ.get(i).setTexto(aux3);
                persistenciaConceptosJuridicos.editar(listCJ.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error editarConceptosJuridicos Admi : " + e.toString());
        }
    }

    @Override
    public void borrarConceptosJuridicos(List<ConceptosJuridicos> listCJ) {
        try {
            for (int i = 0; i < listCJ.size(); i++) {
                persistenciaConceptosJuridicos.borrar(listCJ.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error borrarConceptosJuridicos Admi : " + e.toString());
        }
    }

    @Override
    public List<Empresas> listEmpresas() {
        try {
            List<Empresas> empresas = persistenciaEmpresas.buscarEmpresas();
            return empresas;
        } catch (Exception e) {
            System.out.println("Error listEmpresas Admi : " + e.toString());
            return null;
        }
    }
}
