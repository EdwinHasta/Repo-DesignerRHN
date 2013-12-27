/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Persistencia;

import Entidades.Formulascontratos;
import InterfacePersistencia.PersistenciaFormulasContratosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author PROYECTO01
 */
@Stateless
public class PersistenciaFormulasContratos implements PersistenciaFormulasContratosInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Formulascontratos formulascontratos) {
        try {
            em.persist(formulascontratos);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaFormulasContratos : " + e.toString());
        }
    }

    @Override
    public void editar(Formulascontratos formulascontratos) {
        try {
            em.merge(formulascontratos);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaFormulasContratos : " + e.toString());
        }
    }

    @Override
    public void borrar(Formulascontratos formulascontratos) {
        try {
            em.remove(em.merge(formulascontratos));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaFormulasContratos : " + e.toString());
        }
    }

    @Override
    public List<Formulascontratos> formulasContratosParaFormulaSecuencia(BigInteger secuencia) {
        try {
            Query queryFinal = em.createQuery("SELECT fc FROM Formulascontratos fc WHERE fc.formula.secuencia=:secuencia");
            queryFinal.setParameter("secuencia", secuencia);
            List<Formulascontratos> formulascontratos = queryFinal.getResultList();
            return formulascontratos;
        } catch (Exception e) {
            System.out.println("Error PersistenciaFormulasContratos.formulasContratosParaFormulaSecuencia : " + e.toString());
            return null;
        }
    }
}
