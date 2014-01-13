/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Administrar;

import Entidades.ConceptosJuridicos;
import Entidades.Empresas;
import InterfaceAdministrar.AdministrarConceptoJuridicoInterface;
import InterfacePersistencia.PersistenciaConceptosJuridicosInterface;
import InterfacePersistencia.PersistenciaEmpresasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Clase Stateful. <br>
 * Clase encargada de realizar las operaciones lógicas para la pantalla 'ConceptoJuridico'.
 * @author betelgeuse
 */
@Stateless
public class AdministrarConceptoJuridico implements AdministrarConceptoJuridicoInterface {
    //--------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaConceptosJuridicos'.
     */
    @EJB
    PersistenciaConceptosJuridicosInterface persistenciaConceptosJuridicos;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaEmpresas'.
     */
    @EJB
    PersistenciaEmpresasInterface persistenciaEmpresas;
    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------
    @Override
    public List<ConceptosJuridicos> listaConceptosJuridicosPorEmpresa(BigInteger secuencia) {
        try {
            List<ConceptosJuridicos> conceptos = persistenciaConceptosJuridicos.buscarConceptosJuridicosEmpresa(secuencia);
            return conceptos;
        } catch (Exception e) {
            System.out.println("Error listConceptosJuridicosPorEmpresa Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearConceptosJuridicos(List<ConceptosJuridicos> listCJ) {
        try {
            for (int i = 0; i < listCJ.size(); i++) {                
                persistenciaConceptosJuridicos.crear(listCJ.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearConceptosJuridicos Admi : " + e.toString());
        }
    }

    @Override
    public void editarConceptosJuridicos(List<ConceptosJuridicos> listCJ) {
        try {
            for (int i = 0; i < listCJ.size(); i++) {                
                persistenciaConceptosJuridicos.editar(listCJ.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error editarConceptosJuridicos Admi : " + e.toString());
        }
    }

    @Override
    public void borrarConceptosJuridicos(List<ConceptosJuridicos> listCJ) {
        try {
            for (int i = 0; i < listCJ.size(); i++) {
                persistenciaConceptosJuridicos.borrar(listCJ.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error borrarConceptosJuridicos Admi : " + e.toString());
        }
    }

    @Override
    public List<Empresas> listaEmpresas() {
        try {
            List<Empresas> empresas = persistenciaEmpresas.buscarEmpresas();
            return empresas;
        } catch (Exception e) {
            System.out.println("Error listEmpresas Admi : " + e.toString());
            return null;
        }
    }
}
