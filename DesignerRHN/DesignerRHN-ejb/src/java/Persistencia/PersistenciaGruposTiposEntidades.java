/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import InterfacePersistencia.PersistenciaGruposTiposEntidadesInterface;
import Entidades.Grupostiposentidades;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'GruposTiposEntidades'
 * de la base de datos.
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaGruposTiposEntidades implements PersistenciaGruposTiposEntidadesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    /*@PersistenceContext(unitName = "DesignerRHN-ejbPU")
     EntityManager em;*/
    @Override
    public void crear(EntityManager em, Grupostiposentidades gruposTiposEntidades) {
        try {
            em.clear();
            em.getTransaction().begin();
            em.persist(gruposTiposEntidades);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaGruposTiposEntidades crear ERROR : " + e);
        }
    }

    @Override
    public void editar(EntityManager em, Grupostiposentidades gruposTiposEntidades) {
        System.out.println("PERSISTENCIA-------------");
        System.out.println("CODIGO " + gruposTiposEntidades.getCodigo());
        System.out.println("NOMBRE " + gruposTiposEntidades.getNombre());
        System.out.println("-------------PERSISTENCIA");
        try {
            em.clear();
            em.getTransaction().begin();
            em.merge(gruposTiposEntidades);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaGruposTiposEntidades editar error " + e);
        }
    }

    @Override
    public void borrar(EntityManager em, Grupostiposentidades gruposTiposEntidades) {
        try {
            System.out.println("PERSISTENCIA-------------");
            System.out.println("----------BORRAR--------------------------------");
            System.out.println("CODIGO " + gruposTiposEntidades.getCodigo());
            System.out.println("NOMBRE " + gruposTiposEntidades.getNombre());
            System.out.println("-------------PERSISTENCIA");
            em.clear();
            em.getTransaction().begin();
            em.remove(em.merge(gruposTiposEntidades));
            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("\n ERROR EN PersistenciaGruposTiposEntidades borrar ERROR " + e.toString());
        }
    }

    @Override
    public Grupostiposentidades consultarGrupoTipoEntidad(EntityManager em, BigInteger secuencia) {
        try {

            return em.find(Grupostiposentidades.class, secuencia);
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaGruposTiposEntidades buscarGrupoTipoEntidad ERROR " + e);

            return null;
        }
    }

    @Override
    public List<Grupostiposentidades> consultarGruposTiposEntidades(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT ta FROM Grupostiposentidades ta ORDER BY ta.codigo");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Grupostiposentidades> todosGrupostiposentidades = query.getResultList();
            return todosGrupostiposentidades;
        } catch (Exception e) {
            System.err.println("Error: PersistenciaGrupostiposentidades consultarGrupostiposentidades ERROR " + e);
            return null;
        }
    }

    public BigInteger contarTiposEntidadesGrupoTipoEntidad(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*) FROM tiposentidades WHERE grupo =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PersistenciaGrupostiposentidades contarTiposEntidadesGrupoTipoEntidad Retorno " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PersistenciaGrupostiposentidades contarTiposEntidadesGrupoTipoEntidad ERROR : " + e);
            return retorno;
        }
    }

    public BigInteger contarTSgruposTiposEntidadesTipoEntidad(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*) FROM tsgrupostiposentidades WHERE grupotipoentidad =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PersistenciaGrupostiposentidades contarTSgruposTiposEntidadesTipoEntidad Retorno " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PersistenciaGrupostiposentidades contarTSgruposTiposEntidadesTipoEntidad ERROR : " + e);
            return retorno;
        }
    }

}
