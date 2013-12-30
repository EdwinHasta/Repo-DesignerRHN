package Persistencia;

import Entidades.EstructurasFormulas;
import InterfacePersistencia.PersistenciaEstructurasFormulasInterface;
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
public class PersistenciaEstructurasFormulas implements PersistenciaEstructurasFormulasInterface {

    
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public List<EstructurasFormulas> estructurasFormulasParaHistoriaFormula(BigInteger secuencia) {
        try {
            String sqlQuery = "select rownum ID, LEVEL NIVEL, vw.posicion POSICION,\n"
                    + "decode(vw.descripcion, null, \n"
                    + "lpad(' ',level*5,' ')|| \n"
                    + "signo, \n"
                    + "lpad(' ',LEVEL*5,' ')||vw.nombre) NOMBRENODO, \n"
                    + "vw.nodo NODO,\n"
                    + "vw.descripcion DESCRIPCION,\n"
                    + "vw.formula FORMULA, \n"
                    + "vw.formulahijo FORMULAHIJO, \n"
                    + "vw.historiaformula HISTORIAFORMULA, \n"
                    + "vw.historiaformula HISTORIAFORMULAHIJO\n"
                    + "from vwarbolesformulas vw\n"
                    + "START WITH vw.historiaformula = ? \n"
                    + "CONNECT BY PRIOR vw.formulahijo = vw.formula\n"
                    + "ORDER BY LEVEL, POSICION";
            Query query = em.createNativeQuery(sqlQuery, "ConsultaEstructurasFormulas");
            query.setParameter(1, secuencia);
            List<EstructurasFormulas> listaEstructurasFormulas = query.getResultList();
            return listaEstructurasFormulas;
        } catch (Exception e) {
            System.out.println("Error PersistenciaEstructurasFormulas.estructurasFormulasParaHistoriaFormula. " + e.toString());
            return null;
        }
    }
}
