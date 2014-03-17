/**
 * Documentación a cargo de AndresPineda
 */
package Administrar;

import Entidades.CentrosCostos;
import Entidades.Circulares;
import Entidades.Empresas;
import Entidades.Monedas;
import Entidades.VigenciasMonedasBases;
import InterfaceAdministrar.AdministrarEmpresasInterface;
import InterfacePersistencia.PersistenciaCentrosCostosInterface;
import InterfacePersistencia.PersistenciaCircularesInterface;
import InterfacePersistencia.PersistenciaEmpresasInterface;
import InterfacePersistencia.PersistenciaMonedasInterface;
import InterfacePersistencia.PersistenciaVigenciasMonedasBasesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 * Clase Stateful. <br>
 * Clase encargada de realizar las operaciones lógicas para la pantalla
 * 'Empresas'.
 *
 * @author AndresPineda
 */
@Stateful
public class AdministrarEmpresas implements AdministrarEmpresasInterface{
    //--------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaEmpresas'.
     */
    @EJB
    PersistenciaEmpresasInterface persistenciaEmpresas;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaCirculares'.
     */
    @EJB
    PersistenciaCircularesInterface persistenciaCirculares;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaVigenciasMonedasBases'.
     */
    @EJB
    PersistenciaVigenciasMonedasBasesInterface persistenciaVigenciasMonedasBases;
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
     * 'persistenciaMonedas'.
     */
    @EJB
    PersistenciaMonedasInterface persistenciaMonedas;

    //________________---------------_________________-------------------_______________//
    @Override
    public List<Empresas> listaEmpresas() {
        try {
            List<Empresas> lista = persistenciaEmpresas.buscarEmpresas();
            return lista;
        } catch (Exception e) {
            System.err.println("Error listaEmpresas Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearEmpresas(List<Empresas> listaE) {
        try {
            for (int i = 0; i < listaE.size(); i++) {
                persistenciaEmpresas.crear(listaE.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearEmpresas Admi : " + e.toString());
        }
    }

    @Override
    public void editarEmpresas(List<Empresas> listaE) {
        try {
            for (int i = 0; i < listaE.size(); i++) {
                persistenciaEmpresas.editar(listaE.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error editarEmpresas Admi : " + e.toString());
        }
    }

    @Override
    public void borrarEmpresas(List<Empresas> listaE) {
        try {
            for (int i = 0; i < listaE.size(); i++) {
                persistenciaEmpresas.borrar(listaE.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error borrarEmpresas Admi : " + e.toString());
        }
    }

    @Override
    public List<Circulares> listaCircularesParaEmpresa(BigInteger secuencia) {
        try {
            List<Circulares> lista = persistenciaCirculares.buscarCircularesPorSecuenciaEmpresa(secuencia);
            return lista;
        } catch (Exception e) {
            System.out.println("Error listaCircularesParaEmpresa Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearCirculares(List<Circulares> listaC) {
        try {
            for (int i = 0; i < listaC.size(); i++) {
                persistenciaCirculares.crear(listaC.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearEmpresas Admi : " + e.toString());
        }
    }

    @Override
    public void editarCirculares(List<Circulares> listaC) {
        try {
            for (int i = 0; i < listaC.size(); i++) {
                persistenciaCirculares.editar(listaC.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error editarEmpresas Admi : " + e.toString());
        }
    }

    @Override
    public void borrarCirculares(List<Circulares> listaC) {
        try {
            for (int i = 0; i < listaC.size(); i++) {
                persistenciaCirculares.borrar(listaC.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error borrarEmpresas Admi : " + e.toString());
        }
    }

    @Override
    public List<VigenciasMonedasBases> listaVigenciasMonedasBasesParaEmpresa(BigInteger secuencia) {
        try {
            List<VigenciasMonedasBases> lista = persistenciaVigenciasMonedasBases.buscarVigenciasMonedasBasesPorSecuenciaEmpresa(secuencia);
            return lista;
        } catch (Exception e) {
            System.out.println("Error listaVigenciasMonedasBasesParaEmpresa Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearVigenciasMonedasBases(List<VigenciasMonedasBases> listaVMB) {
        try {
            for (int i = 0; i < listaVMB.size(); i++) {
                persistenciaVigenciasMonedasBases.crear(listaVMB.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearVigenciasMonedasBases Admi : " + e.toString());
        }
    }

    @Override
    public void editarVigenciasMonedasBases(List<VigenciasMonedasBases> listaVMB) {
        try {
            for (int i = 0; i < listaVMB.size(); i++) {
                persistenciaVigenciasMonedasBases.editar(listaVMB.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error editarVigenciasMonedasBases Admi : " + e.toString());
        }
    }

    @Override
    public void borrarVigenciasMonedasBases(List<VigenciasMonedasBases> listaVMB) {
        try {
            for (int i = 0; i < listaVMB.size(); i++) {
                persistenciaVigenciasMonedasBases.borrar(listaVMB.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error borrarVigenciasMonedasBases Admi : " + e.toString());
        }
    }

    @Override
    public List<CentrosCostos> lovCentrosCostos() {
        try {
            List<CentrosCostos> lista = persistenciaCentrosCostos.buscarCentrosCostos();
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovCentrosCostos Admi : " + e.toString());
            return null;
        }
    }
     @Override
    public List<Monedas> lovMonedas() {
        try {
            List<Monedas> lista = persistenciaMonedas.consultarMonedas();
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovMonedas Admi : " + e.toString());
            return null;
        }
    }

}
