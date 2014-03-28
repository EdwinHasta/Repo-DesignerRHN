/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Conceptos;
import Entidades.ValoresConceptos;
import InterfaceAdministrar.AdministrarValoresConceptosInterface;
import InterfacePersistencia.PersistenciaConceptosInterface;
import InterfacePersistencia.PersistenciaValoresConceptosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarValoresConceptos implements AdministrarValoresConceptosInterface {

    //--------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaValoresConceptos'.
     */
    @EJB
    PersistenciaValoresConceptosInterface persistenciaValoresConceptos;
    @EJB
    PersistenciaConceptosInterface persistenciaConceptos;
    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------

    public void modificarValoresConceptos(List<ValoresConceptos> listaValoresConceptos) {
        for (int i = 0; i < listaValoresConceptos.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaValoresConceptos.editar(listaValoresConceptos.get(i));
        }
    }

    public void borrarValoresConceptos(List<ValoresConceptos> listaValoresConceptos) {
        for (int i = 0; i < listaValoresConceptos.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaValoresConceptos.borrar(listaValoresConceptos.get(i));
        }
    }

    public void crearValoresConceptos(List<ValoresConceptos> listaValoresConceptos) {
        for (int i = 0; i < listaValoresConceptos.size(); i++) {
            persistenciaValoresConceptos.crear(listaValoresConceptos.get(i));
        }
    }

    @Override
    public List<ValoresConceptos> consultarValoresConceptos() {
        List<ValoresConceptos> listaValoresConceptos;
        listaValoresConceptos = persistenciaValoresConceptos.consultarValoresConceptos();
        return listaValoresConceptos;
    }

    public List<Conceptos> consultarLOVConceptos() {
        List<Conceptos> listLOVConceptos;
        listLOVConceptos = persistenciaConceptos.buscarConceptos();
        return listLOVConceptos;
    }

    @Override
    public BigInteger contarConceptoValorConcepto(BigInteger concepto) {
        BigInteger consulta;

        try {
            consulta = persistenciaValoresConceptos.consultarConceptoValorConcepto(concepto);
            return consulta;
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARVALORESCONCEPTOS CONSULTARCONCEPTOVALORCONCEPTO ERROR : " + e);
            return null;
        }

    }
}
