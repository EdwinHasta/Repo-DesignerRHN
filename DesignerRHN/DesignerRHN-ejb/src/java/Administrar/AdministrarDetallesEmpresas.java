/**
 * Documentación a cargo de AndresPineda
 */
package Administrar;

import Entidades.Cargos;
import Entidades.Ciudades;
import Entidades.DetallesEmpresas;
import Entidades.Empleados;
import Entidades.Empresas;
import Entidades.Personas;
import InterfaceAdministrar.AdministrarDetallesEmpresasInterface;
import InterfacePersistencia.PersistenciaCargosInterface;
import InterfacePersistencia.PersistenciaCiudadesInterface;
import InterfacePersistencia.PersistenciaDetallesEmpresasInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaEmpresasInterface;
import InterfacePersistencia.PersistenciaPersonasInterface;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 * Clase Stateful. <br>
 * Clase encargada de realizar las operaciones lógicas para la pantalla
 * 'DetalleEmpresa'.
 *
 * @author AndresPineda
 */
@Stateful
public class AdministrarDetallesEmpresas implements AdministrarDetallesEmpresasInterface {
    //--------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaDetallesEmpresas'.
     */
    @EJB
    PersistenciaDetallesEmpresasInterface persistenciaDetallesEmpresas;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaCiudades'.
     */
    @EJB
    PersistenciaCiudadesInterface persistenciaCiudades;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaPersonas'.
     */
    @EJB
    PersistenciaPersonasInterface persistenciaPersonas;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaCargos'.
     */
    @EJB
    PersistenciaCargosInterface persistenciaCargos;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaEmpleados'.
     */
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleados;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaEmpresas'.
     */
    @EJB
    PersistenciaEmpresasInterface persistenciaEmpresas;

    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------
    @Override
    public List<DetallesEmpresas> listaDetallesEmpresasPorSecuencia(BigInteger secEmpresa) {
        try {
            List<DetallesEmpresas> lista = null;
            if (secEmpresa == null) {
                lista = persistenciaDetallesEmpresas.buscarDetallesEmpresas();
            } else {
                DetallesEmpresas detalle = persistenciaDetallesEmpresas.buscarDetalleEmpresaPorSecuencia(secEmpresa);
                lista = new ArrayList<DetallesEmpresas>();
                lista.add(detalle);
            }
            return lista;

        } catch (Exception e) {
            System.out.println("Error listaDetallesEmpresasPorSecuencia Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearDetalleEmpresa(List<DetallesEmpresas> listaDE) {
        try {
            for (int i = 0; i < listaDE.size(); i++) {
                if (listaDE.get(i).getCiudaddocumentorepresentante().getSecuencia() == null) {
                    listaDE.get(i).setCiudaddocumentorepresentante(null);
                }
                if (listaDE.get(i).getCargofirmaconstancia().getSecuencia() == null) {
                    listaDE.get(i).setCargofirmaconstancia(null);
                }
                if (listaDE.get(i).getGerentegeneral().getSecuencia() == null) {
                    listaDE.get(i).setGerentegeneral(null);
                }
                if (listaDE.get(i).getPersonafirmaconstancia().getSecuencia() == null) {
                    listaDE.get(i).setPersonafirmaconstancia(null);
                }
                if (listaDE.get(i).getRepresentantecir().getSecuencia() == null) {
                    listaDE.get(i).setRepresentantecir(null);
                }
                if (listaDE.get(i).getSubgerente().getSecuencia() == null) {
                    listaDE.get(i).setSubgerente(null);
                }
                if (listaDE.get(i).getCiudad().getSecuencia() == null) {
                    listaDE.get(i).setCiudad(null);
                }
                persistenciaDetallesEmpresas.crear(listaDE.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearDetalleEmpresa Admi : " + e.toString());
        }
    }

    @Override
    public void editarDetalleEmpresa(List<DetallesEmpresas> listaDE) {
        try {
            for (int i = 0; i < listaDE.size(); i++) {
                if (listaDE.get(i).getCiudaddocumentorepresentante().getSecuencia() == null) {
                    listaDE.get(i).setCiudaddocumentorepresentante(null);
                }
                if (listaDE.get(i).getCargofirmaconstancia().getSecuencia() == null) {
                    listaDE.get(i).setCargofirmaconstancia(null);
                }
                if (listaDE.get(i).getGerentegeneral().getSecuencia() == null) {
                    listaDE.get(i).setGerentegeneral(null);
                }
                if (listaDE.get(i).getPersonafirmaconstancia().getSecuencia() == null) {
                    listaDE.get(i).setPersonafirmaconstancia(null);
                }
                if (listaDE.get(i).getRepresentantecir().getSecuencia() == null) {
                    listaDE.get(i).setRepresentantecir(null);
                }
                if (listaDE.get(i).getSubgerente().getSecuencia() == null) {
                    listaDE.get(i).setSubgerente(null);
                }
                if (listaDE.get(i).getCiudad().getSecuencia() == null) {
                    listaDE.get(i).setCiudad(null);
                }
                persistenciaDetallesEmpresas.editar(listaDE.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error editarDetalleEmpresa Admi : " + e.toString());
        }
    }

    @Override
    public void borrarDetalleEmpresa(List<DetallesEmpresas> listaDE) {
        try {
            for (int i = 0; i < listaDE.size(); i++) {
                if (listaDE.get(i).getCiudaddocumentorepresentante().getSecuencia() == null) {
                    listaDE.get(i).setCiudaddocumentorepresentante(null);
                }
                if (listaDE.get(i).getCargofirmaconstancia().getSecuencia() == null) {
                    listaDE.get(i).setCargofirmaconstancia(null);
                }
                if (listaDE.get(i).getGerentegeneral().getSecuencia() == null) {
                    listaDE.get(i).setGerentegeneral(null);
                }
                if (listaDE.get(i).getPersonafirmaconstancia().getSecuencia() == null) {
                    listaDE.get(i).setPersonafirmaconstancia(null);
                }
                if (listaDE.get(i).getRepresentantecir().getSecuencia() == null) {
                    listaDE.get(i).setRepresentantecir(null);
                }
                if (listaDE.get(i).getSubgerente().getSecuencia() == null) {
                    listaDE.get(i).setSubgerente(null);
                }
                if (listaDE.get(i).getCiudad().getSecuencia() == null) {
                    listaDE.get(i).setCiudad(null);
                }
                persistenciaDetallesEmpresas.borrar(listaDE.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error borrarDetalleEmpresa Admi : " + e.toString());
        }
    }

    @Override
    public List<Ciudades> lovCiudades() {
        try {
            List<Ciudades> lista = persistenciaCiudades.ciudades();
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovCiudades Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Empleados> lovEmpleados() {
        try {
            List<Empleados> lista = persistenciaEmpleados.buscarEmpleados();
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovEmpleados Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Personas> lovPersonas() {
        try {
            List<Personas> lista = persistenciaPersonas.consultarPersonas();
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovPersonas Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Cargos> lovCargos() {
        try {
            List<Cargos> lista = persistenciaCargos.consultarCargos();
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovCargos Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Empresas> lovEmpresas() {
        try {
            List<Empresas> lista = persistenciaEmpresas.consultarEmpresas();
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovEmpresas Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public Empresas empresaActual(BigInteger secEmpresa) {
        try {
            Empresas empr = persistenciaEmpresas.buscarEmpresasSecuencia(secEmpresa);
            return empr;
        } catch (Exception e) {
            System.out.println("Error empresaActual Admi : " + e.toString());
            return null;
        }
    }

}
