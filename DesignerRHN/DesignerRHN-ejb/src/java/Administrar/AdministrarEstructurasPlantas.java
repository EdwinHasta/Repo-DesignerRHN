/**
 * Documentación a cargo de AndresPineda
 */
package Administrar;

import Entidades.CentrosCostos;
import Entidades.Estructuras;
import Entidades.Organigramas;
import InterfaceAdministrar.AdministrarEstructurasPlantasInterface;
import InterfaceAdministrar.AdministrarSesionesInterface;
import InterfacePersistencia.PersistenciaCentrosCostosInterface;
import InterfacePersistencia.PersistenciaEstructurasInterface;
import InterfacePersistencia.PersistenciaOrganigramasInterface;
import InterfacePersistencia.PersistenciaPlantasPersonalesInterface;
import InterfacePersistencia.PersistenciaVWActualesCargosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;

/**
 * Clase Stateful. <br>
 * Clase encargada de realizar las operaciones lógicas para la pantalla
 * 'EstructuraPlanta'.
 *
 * @author AndresPineda
 */
@Stateful
public class AdministrarEstructurasPlantas implements AdministrarEstructurasPlantasInterface {

    //-------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaOrganigramas'.
     */
    @EJB
    PersistenciaOrganigramasInterface persistenciaOrganigramas;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaEstructuras'.
     */
    @EJB
    PersistenciaEstructurasInterface persistenciaEstructuras;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaCentrosCostos'.
     */
    @EJB
    PersistenciaCentrosCostosInterface persistenciaCentrosCostos;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaVWActualesCargos'.
     */
    @EJB
    PersistenciaVWActualesCargosInterface persistenciaVWActualesCargos;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaPlantasPersonales'.
     */
    @EJB
    PersistenciaPlantasPersonalesInterface persistenciaPlantasPersonales;
    /**
     * Enterprise JavaBean.<br>
     * Atributo que representa todo lo referente a la conexión del usuario que
     * está usando el aplicativo.
     */
    @EJB
    AdministrarSesionesInterface administrarSesiones;

    private EntityManager em;

    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------
    @Override
    public void obtenerConexion(String idSesion) {
        em = administrarSesiones.obtenerConexionSesion(idSesion);
    }

    @Override
    public List<Organigramas> listaOrganigramas() {
        try {
            List<Organigramas> lista = persistenciaOrganigramas.buscarOrganigramas(em);
            return lista;
        } catch (Exception e) {
            System.out.println("Error listaOrganigramas Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void modificarOrganigramas(List<Organigramas> listaO) {
        try {
            for (int i = 0; i < listaO.size(); i++) {
                persistenciaOrganigramas.editar(em,listaO.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error modificarOrganigramas Admi : " + e.toString());
        }
    }

    @Override
    public String cantidadCargosAControlar(BigInteger secEstructura) {
        try {
            String retorno = "";
            BigInteger valor = persistenciaPlantasPersonales.consultarCantidadEstructuras(em,secEstructura);
            if (valor != null) {
                retorno = String.valueOf(valor);
            }
            return retorno;
        } catch (Exception e) {
            System.out.println("Error cantidadCargosAControlar Admi : " + e.toString());
            String retorno = "";
            return retorno;
        }
    }

    @Override
    public String cantidadCargosEmplActivos(BigInteger secEstructura) {
        try {
            String retorno = "0";
            Long valor = persistenciaVWActualesCargos.conteoCodigosEmpleados(em,secEstructura);
            if (valor > 0 && valor != null) {
                retorno = String.valueOf(valor);
            }
            return retorno;
        } catch (Exception e) {
            System.out.println("Error cantidadCargosEmplActivos Admi : " + e.toString());
            String retorno = "0";
            return retorno;
        }
    }

    @Override
    public List<Estructuras> listaEstructurasPorSecuenciaOrganigrama(BigInteger secOrganigrama) {
        try {
            List<Estructuras> lista = persistenciaEstructuras.buscarEstructurasPorOrganigrama(em,secOrganigrama);
            int tam = lista.size();
            if (tam > 0) {
                for (int i = 0; i < tam; i++) {
                    BigInteger cantidad = persistenciaPlantasPersonales.consultarCantidadEstructuras(em,lista.get(i).getSecuencia());
                    Long real = persistenciaVWActualesCargos.conteoCodigosEmpleados(em,lista.get(i).getSecuencia());
                    if (cantidad != null) {
                        lista.get(i).setCantidadCargosControlar(cantidad.toString());
                    } else {
                        lista.get(i).setCantidadCargosControlar("0");
                    }
                    if (real != null) {
                        lista.get(i).setCantidadCargosEmplActivos(real.toString());
                    } else {
                        lista.get(i).setCantidadCargosEmplActivos("0");
                    }
                }
            }
            return lista;
        } catch (Exception e) {
            System.out.println("Error listaEstructurasPorSecuenciaOrganigrama Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearEstructura(List<Estructuras> listaE) {
        try {
            for (int i = 0; i < listaE.size(); i++) {
                persistenciaEstructuras.crear(em,listaE.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearEstructura Admi : " + e.toString());
        }
    }

    @Override
    public void editarEstructura(List<Estructuras> listaE) {
        try {
            for (int i = 0; i < listaE.size(); i++) {
                persistenciaEstructuras.editar(em,listaE.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error editarEstructura Admi : " + e.toString());
        }
    }

    @Override
    public void borrarEstructura(List<Estructuras> listaE) {
        try {
            for (int i = 0; i < listaE.size(); i++) {
                persistenciaEstructuras.borrar(em,listaE.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error borrarEstructura Admi : " + e.toString());
        }
    }

    @Override
    public List<Estructuras> lovEstructurasPadres(BigInteger secOrganigrama, BigInteger secEstructura) {
        try {
            List<Estructuras> lista = persistenciaEstructuras.buscarEstructurasPadres(em,secOrganigrama, secEstructura);
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovEstructurasPadres Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<CentrosCostos> lovCentrosCostos(BigInteger secEmpresa) {
        try {
            List<CentrosCostos> lista = persistenciaCentrosCostos.buscarCentroCostoPorSecuenciaEmpresa(em,secEmpresa);
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovCentrosCostos Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Estructuras> lovEstructuras() {
        try {
            List<Estructuras> lista = persistenciaEstructuras.buscarEstructuras(em);
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovEstructuras Admi : " + e.toString());
            return null;
        }
    }

}
