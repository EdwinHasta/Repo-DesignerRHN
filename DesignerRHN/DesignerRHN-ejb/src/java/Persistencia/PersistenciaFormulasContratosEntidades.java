/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.FormulasContratosEntidades;
import Entidades.Formulascontratos;
import InterfacePersistencia.PersistenciaFormulasContratosEntidadesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class PersistenciaFormulasContratosEntidades implements PersistenciaFormulasContratosEntidadesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public void crear(FormulasContratosEntidades formulasAseguradas) {
        try {
            em.persist(formulasAseguradas);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaFormulasContratosEntidades");
        }
    }

    public void editar(FormulasContratosEntidades formulasAseguradas) {
        try {
            em.merge(formulasAseguradas);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaFormulasContratosEntidades ERROR : " + e);
        }
    }

    public void borrar(FormulasContratosEntidades formulasAseguradas) {
        try {
            em.remove(em.merge(formulasAseguradas));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaFormulasContratosEntidades ERROR : " + e);
        }
    }

    public List<FormulasContratosEntidades> consultarFormulasContratosEntidades() {
        try {
            Query query = em.createQuery("SELECT te FROM FormulasContratosEntidades te");
            List<FormulasContratosEntidades> formulasAseguradas = query.getResultList();
            return formulasAseguradas;
        } catch (Exception e) {
            System.out.println("Error consultarFormulasContratosEntidades ERROR : " + e);
            return null;
        }
    }

    public List<FormulasContratosEntidades> consultarFormulasContratosEntidadesPorFormulaContrato(BigInteger secFormulaContrato) {
        try {
            Query query = em.createQuery("SELECT te FROM FormulasContratosEntidades te WHERE te.formulacontrato.secuencia = :formulaContrato");
            query.setParameter("formulaContrato", secFormulaContrato);
            List<FormulasContratosEntidades> formulasAseguradas = query.getResultList();
            return formulasAseguradas;
        } catch (Exception e) {
            System.out.println("Error consultarFormulasContratosEntidadesPorFormulaContrato ERROR : " + e);
            return null;
        }
    }

    

    public FormulasContratosEntidades consultarFormulaContratoEntidad(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT te FROM FormulasContratosEntidades te WHERE te.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            FormulasContratosEntidades formulasAseguradas = (FormulasContratosEntidades) query.getSingleResult();
            return formulasAseguradas;
        } catch (Exception e) {
            System.out.println("Error consultarFormulasContratosEntidades");
            FormulasContratosEntidades formulasAseguradas = null;
            return formulasAseguradas;
        }
    }
}