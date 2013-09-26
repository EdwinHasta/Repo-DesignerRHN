/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.Instituciones;
import InterfacePersistencia.PersistenciaInstitucionesInterface;
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
public class PersistenciaInstituciones implements PersistenciaInstitucionesInterface{

   @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear TiposTelefonos.
     */
    @Override
    public void crear(Instituciones instituciones) {
        try {
            em.persist(instituciones);
        } catch (Exception e) {
            System.out.println("Error almacenar en persistencia tipos telefonos");
        }
    }

    /*
     *Editar TiposTelefono. 
     */
    @Override
    public void editar(Instituciones instituciones) {
        em.merge(instituciones);
    }

    /*
     *Borrar TiposTelefono.
     */
    @Override
    public void borrar(Instituciones instituciones) {
        try {
            em.remove(em.merge(instituciones));
        } catch (Exception e) {
            System.out.println("Error Instituciones.borrar");
        }
    }

    /*
     *Encontrar un TipoTelefono. 
     */
    @Override
    public Instituciones buscarInstitucion(BigInteger id) {
        try {
            BigInteger secuencia = new BigInteger(id.toString());
            //return em.find(Empleados.class, id);
            return em.find(Instituciones.class, secuencia);
        } catch (Exception e) {
            return null;
        }

    }

   @Override
    public List<Instituciones> instituciones() {
        try {
            Query query = em.createQuery("SELECT i FROM Instituciones i");
            List<Instituciones> listaInstituciones = query.getResultList();
            return listaInstituciones;
        } catch (Exception e) {
            System.out.println("Error PersistenciaTiposTelefonos.tiposTelefonos" + e);
            return null;
        }
    }
}
