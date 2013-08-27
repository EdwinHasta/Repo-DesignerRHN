package Persistencia;

import Entidades.Conceptos;
import InterfacePersistencia.PersistenciaConceptosInterface;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaConceptos implements PersistenciaConceptosInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    private List<BigInteger> secEmpresas = new ArrayList<BigInteger>();
    private List<Conceptos> listConceptos = new ArrayList<Conceptos>();
    private List<Conceptos> listTotalConceptos;

    /*
     * Crear concepto.
     */
    public void crear(Conceptos concepto) {
        em.persist(concepto);
    }

    /*
     *Editar concepto. 
     */
    public void editar(Conceptos concepto) {
        em.merge(concepto);
    }

    /*
     *Borrar concepto.
     */
    public void borrar(Conceptos concepto) {
        em.remove(em.merge(concepto));
    }

    /*
     *Encontrar una persona. 
     */
    public Conceptos buscarPersona(Object id) {
        return em.find(Conceptos.class, id);
    }

    /*
     *Encontrar todas las personas. 
     */
    public List<Conceptos> buscarPersonas() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Conceptos.class));
        return em.createQuery(cq).getResultList();
    }

    public void revisarConcepto(int codigoConcepto) {
        try {
            /*Query queryEmp = em.createQuery("SELECT e.secuencia from Empresas e");
             secEmpresas = queryEmp.getResultList();
             for (int i = 0; i < secEmpresas.size(); i++) {
             System.out.println(secEmpresas.get(i));
             }
             for (int i = 0; i < secEmpresas.size(); i++) {
             BigInteger empresa = secEmpresas.get(i);
             Query queryC = em.createQuery("SELECT c from Conceptos c where c.empresa.secuencia = :empresa");
             queryC.setParameter("empresa", empresa);
             listConceptos.addAll(queryC.getResultList());
             }*/
            Query queryC = em.createQuery("SELECT c from Conceptos c where c.empresa.secuencia IN (SELECT e.secuencia from Empresas e)");
            listConceptos = queryC.getResultList();
            System.out.println("Tamaño: " + listConceptos.size());

        } catch (Exception e) {
            System.out.println("Se estallo." + e);
        }
    }

    public boolean verificarCodigoConcepto(BigInteger codigoConcepto) {
        try {
            Query query = em.createQuery("SELECT COUNT(c) FROM Conceptos c WHERE c.codigo = :codigo");
            query.setParameter("codigo", codigoConcepto);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Exepcion: " + e);
            return false;
        }
    }

    public Conceptos validarCodigoConcepto(BigInteger codigoConcepto, BigInteger secEmpresa) {
        Conceptos concepto = new Conceptos();
        try {
            Query query = em.createQuery("SELECT c FROM Conceptos c WHERE c.codigo = :codigo AND c.empresa.secuencia = :secEmpresa");
            query.setParameter("codigo", codigoConcepto);
            query.setParameter("secEmpresa", secEmpresa);
            concepto = (Conceptos) query.getSingleResult();
            return concepto;
        } catch (Exception e) {
            return null;
        }
    }
}
