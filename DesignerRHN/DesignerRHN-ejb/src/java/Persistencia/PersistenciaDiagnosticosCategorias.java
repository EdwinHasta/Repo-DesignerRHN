/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.Diagnosticoscategorias;
import InterfacePersistencia.PersistenciaDiagnosticosCategoriasInterface;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PersistenciaDiagnosticosCategorias implements PersistenciaDiagnosticosCategoriasInterface{

     @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Diagnosticoscategorias diagnosticosCategorias) {
        em.persist(diagnosticosCategorias);
    }

    @Override
    public void editar(Diagnosticoscategorias diagnosticosCategorias) {
        em.merge(diagnosticosCategorias);
    }

    @Override
    public void borrar(Diagnosticoscategorias diagnosticosCategorias) {
        em.remove(em.merge(diagnosticosCategorias));
    }

   /* public List<EnfermeadadesProfesionales> buscarEPPorEmpleado(BigInteger secEmpleado) {
        try {
            Query query = em.createQuery("SELECT ep FROM EnfermeadadesProfesionales ep WHERE ep.empleado.secuencia = :secuenciaEmpl ORDER BY ep.fechanotificacion DESC");
            query.setParameter("secuenciaEmpl", secEmpleado);

            List<EnfermeadadesProfesionales> enfermedadesProfesionales = query.getResultList();
            return enfermedadesProfesionales;
        } catch (Exception e) {
            System.out.println("Error en PersistenciaEnfermedadesProfesionales Por Empleados ERROR" + e);
            return null;
        }
    }*/
    
}
