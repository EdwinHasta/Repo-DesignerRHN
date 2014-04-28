/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarConceptosSoportesInterface;
import Entidades.Operandos;
import Entidades.Conceptos;
import Entidades.ConceptosSoportes;
import InterfacePersistencia.PersistenciaOperandosInterface;
import InterfacePersistencia.PersistenciaConceptosInterface;
import InterfacePersistencia.PersistenciaConceptosSoportesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarConceptosSoportes implements AdministrarConceptosSoportesInterface {
//--------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    

    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaConceptosSoportes'.
     */
    @EJB
    PersistenciaConceptosSoportesInterface persistenciaConceptosSoportes;
    @EJB
    PersistenciaOperandosInterface persistenciaOperandos;
    @EJB
    PersistenciaConceptosInterface persistenciaConceptos;
    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------

    public void modificarConceptosSoportes(List<ConceptosSoportes> listaConceptosSoportes) {
        for (int i = 0; i < listaConceptosSoportes.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaConceptosSoportes.editar(listaConceptosSoportes.get(i));
        }
    }

    public void borrarConceptosSoportes(List<ConceptosSoportes> listaConceptosSoportes) {
        for (int i = 0; i < listaConceptosSoportes.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaConceptosSoportes.borrar(listaConceptosSoportes.get(i));
        }
    }

    public void crearConceptosSoportes(List<ConceptosSoportes> listaConceptosSoportes) {
        for (int i = 0; i < listaConceptosSoportes.size(); i++) {
            persistenciaConceptosSoportes.crear(listaConceptosSoportes.get(i));
        }
    }

    @Override
    public List<ConceptosSoportes> consultarConceptosSoportes() {
        List<ConceptosSoportes> listaConceptosSoportes;
        listaConceptosSoportes = persistenciaConceptosSoportes.consultarConceptosSoportes();
        return listaConceptosSoportes;
    }

    public List<Operandos> consultarLOVOperandos() {
        List<Operandos> listLOVOperandos;
        listLOVOperandos = persistenciaOperandos.buscarOperandos();
        return listLOVOperandos;
    }

    @Override
    public List<Operandos> consultarLOVOperandosPorConcepto(BigInteger secConceptoSoporte) {
        List<Operandos> listLOVOperandos;
        listLOVOperandos = persistenciaOperandos.operandoPorConceptoSoporte(secConceptoSoporte);
        return listLOVOperandos;
    }

    public List<Conceptos> consultarLOVConceptos() {
        List<Conceptos> listLOVConceptos;
        listLOVConceptos = persistenciaConceptos.buscarConceptos();
        return listLOVConceptos;
    }

    public BigInteger contarConceptosOperandos(BigInteger concepto, BigInteger operando) {
        BigInteger contarConceptosOperandos = persistenciaConceptosSoportes.consultarConceptoSoporteConceptoOperador(concepto, operando);
        return contarConceptosOperandos;
    }
}
