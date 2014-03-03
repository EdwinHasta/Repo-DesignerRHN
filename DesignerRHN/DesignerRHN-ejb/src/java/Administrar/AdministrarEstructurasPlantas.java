/**
 * Documentación a cargo de AndresPineda
 */
package Administrar;

import Entidades.CentrosCostos;
import Entidades.Estructuras;
import Entidades.Organigramas;
import InterfaceAdministrar.AdministrarEstructurasPlantasInterface;
import InterfacePersistencia.PersistenciaCentrosCostosInterface;
import InterfacePersistencia.PersistenciaEstructurasInterface;
import InterfacePersistencia.PersistenciaOrganigramasInterface;
import InterfacePersistencia.PersistenciaPlantasPersonalesInterface;
import InterfacePersistencia.PersistenciaVWActualesCargosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

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

    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------
    @Override
    public List<Organigramas> listaOrganigramas() {
        try {
            List<Organigramas> lista = persistenciaOrganigramas.buscarOrganigramas();
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
                persistenciaOrganigramas.editar(listaO.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error modificarOrganigramas Admi : " + e.toString());
        }
    }

    @Override
    public String cantidadCargosAControlar(BigInteger secEstructura) {
        try {
            String retorno = "";
            BigInteger valor = persistenciaPlantasPersonales.consultarCantidadEstructuras(secEstructura);
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
            Long valor = persistenciaVWActualesCargos.conteoCodigosEmpleados(secEstructura);
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
            List<Estructuras> lista = persistenciaEstructuras.buscarEstructurasPorOrganigrama(secOrganigrama);
            int tam = lista.size();
            if (tam > 0) {
                for (int i = 0; i < tam; i++) {
                    BigInteger cantidad = persistenciaPlantasPersonales.consultarCantidadEstructuras(lista.get(i).getSecuencia());
                    Long real = persistenciaVWActualesCargos.conteoCodigosEmpleados(lista.get(i).getSecuencia());
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
                persistenciaEstructuras.crear(listaE.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearEstructura Admi : " + e.toString());
        }
    }

    @Override
    public void editarEstructura(List<Estructuras> listaE) {
        try {
            for (int i = 0; i < listaE.size(); i++) {
                persistenciaEstructuras.editar(listaE.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error editarEstructura Admi : " + e.toString());
        }
    }

    @Override
    public void borrarEstructura(List<Estructuras> listaE) {
        try {
            for (int i = 0; i < listaE.size(); i++) {
                persistenciaEstructuras.borrar(listaE.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error borrarEstructura Admi : " + e.toString());
        }
    }

    @Override
    public List<Estructuras> lovEstructurasPadres(BigInteger secOrganigrama, BigInteger secEstructura) {
        try {
            List<Estructuras> lista = persistenciaEstructuras.buscarEstructurasPadres(secOrganigrama, secEstructura);
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovEstructurasPadres Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<CentrosCostos> lovCentrosCostos(BigInteger secEmpresa) {
        try {
            List<CentrosCostos> lista = persistenciaCentrosCostos.buscarCentroCostoPorSecuenciaEmpresa(secEmpresa);
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovCentrosCostos Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Estructuras> lovEstructuras() {
        try {
            List<Estructuras> lista = persistenciaEstructuras.buscarEstructuras();
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovEstructuras Admi : " + e.toString());
            return null;
        }
    }

}
