/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
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
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'Instituciones'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaInstituciones implements PersistenciaInstitucionesInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
   @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Instituciones instituciones) {
        try {
            em.persist(instituciones);
        } catch (Exception e) {
            System.out.println("Error almacenar en persistencia tipos telefonos");
        }
    }

    @Override
    public void editar(Instituciones instituciones) {
        em.merge(instituciones);
    }

    @Override
    public void borrar(Instituciones instituciones) {
        try {
            em.remove(em.merge(instituciones));
        } catch (Exception e) {
            System.out.println("Error Instituciones.borrar");
        }
    }

    @Override
    public Instituciones buscarInstitucion(BigInteger secuencia) {
        try {
            BigInteger sec = new BigInteger(secuencia.toString());
            return em.find(Instituciones.class, sec);
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
