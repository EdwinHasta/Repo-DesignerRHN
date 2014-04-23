/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.NormasLaborales;
import InterfacePersistencia.PersistenciaNormasLaboralesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'NormasLaborales'
 * (Para verificar que esta asociado a una VigenciaNormaEmpleado, se realiza la
 * operacion sobre la tabla VigenciasNormasEmpleados) de la base de datos.
 *
 * @author betelgeuse
 */
@Stateless
@LocalBean
public class PersistenciaNormasLaborales implements PersistenciaNormasLaboralesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(NormasLaborales normasLaborales) {
        em.persist(normasLaborales);
    }

    @Override
    public void editar(NormasLaborales normasLaborales) {
        em.merge(normasLaborales);
    }

    @Override
    public void borrar(NormasLaborales normasLaborales) {
        em.remove(em.merge(normasLaborales));
    }

    @Override
    public NormasLaborales consultarNormaLaboral(BigInteger secuenciaNL) {
        try {
            return em.find(NormasLaborales.class, secuenciaNL);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<NormasLaborales> consultarNormasLaborales() {
        Query query = em.createQuery("SELECT m FROM NormasLaborales m");
        List<NormasLaborales> lista = query.getResultList();
        return lista;
    }

    @Override
    public BigInteger contarVigenciasNormasEmpleadosNormaLaboral(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            Query query = em.createQuery("SELECT count(vn) FROM VigenciasNormasEmpleados vn WHERE vn.normalaboral.secuencia =:secNormaLaboral ");
            query.setParameter("secNormaLaboral", secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.err.println("PersistenciaMotivosCambiosSueldos retorno ==" + retorno.intValue());

        } catch (Exception e) {
            System.err.println("ERROR EN PersistenciaMotivosCambiosSueldos verificarBorradoVigenciasSueldos ERROR :" + e);
        } finally {
            return retorno;
        }
    }
}
