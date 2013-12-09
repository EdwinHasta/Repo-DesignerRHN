package Administrar;

import Entidades.Conexiones;
import Entidades.Perfiles;
import Entidades.Recordatorios;
import InterfaceAdministrar.AdministrarInicioRedInterface;
import InterfacePersistencia.EntityManagerGlobalInterface;
import InterfacePersistencia.PersistenciaActualUsuarioInterface;
import InterfacePersistencia.PersistenciaConexionInicialInterface;
import InterfacePersistencia.PersistenciaConexionesInterface;
import InterfacePersistencia.PersistenciaEmpresasInterface;
import InterfacePersistencia.PersistenciaRecordatoriosInterface;
import Persistencia.EntityManagerGlobal;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;

@Stateful
public class AdministrarInicioRed implements AdministrarInicioRedInterface, Serializable {

    @EJB
    PersistenciaConexionInicialInterface persistenciaConexionInicial;
    @EJB
    PersistenciaRecordatoriosInterface persistenciaRecordatorios;
    @EJB
    PersistenciaEmpresasInterface persistenciaEmpresas;
    @EJB
    EntityManagerGlobalInterface FactoryGlobal;
    @EJB
    PersistenciaConexionesInterface persistenciaConexiones;
    @EJB
    PersistenciaActualUsuarioInterface persistenciaActualUsuario;
    private EntityManager em;
    private Perfiles perfilUsuario;
    private BigInteger secPerfil;

    public AdministrarInicioRed() {
        FactoryGlobal = new EntityManagerGlobal();
    }

    public boolean validacionLogin(String baseDatos, String usuario, String contraseña) {
        FactoryGlobal.getEmf().close();
        if (conexionInicial(baseDatos)) {
            if (validarUsuario(usuario)) {
                secPerfil = persistenciaConexionInicial.usuarioLogin(FactoryGlobal.getEmf().createEntityManager(), usuario);
                perfilUsuario = persistenciaConexionInicial.perfilUsuario(FactoryGlobal.getEmf().createEntityManager(), secPerfil);
                FactoryGlobal.getEmf().close();
                if (conexionUsuario(baseDatos, usuario, contraseña)) {
                    if (validarConexionUsuario(usuario, contraseña, baseDatos)) {
                        persistenciaConexionInicial.setearUsuario(em, perfilUsuario.getDescripcion(), perfilUsuario.getPwd());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean conexionDefault() {
        try {
            conexionInicial("XE");
            persistenciaConexionInicial.accesoDefault(FactoryGlobal.getEmf().createEntityManager());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean conexionInicial(String baseDatos) {
        try {
            if (FactoryGlobal.getEmf() != null && FactoryGlobal.getEmf().isOpen()) {
                FactoryGlobal.getEmf().close();
            }
            //emf = Persistence.createEntityManagerFactory(baseDatos);
            if (FactoryGlobal.crearFactoryInicial(baseDatos)) {
                return true;
            } else {
                //System.out.println("BASE DE DATOS NO EXISTE");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error AdministrarLogin.conexionInicial" + e);
            return false;
        }
    }

    public boolean conexionUsuario(String baseDatos, String usuario, String contraseña) {
        try {
            secPerfil = persistenciaConexionInicial.usuarioLogin(FactoryGlobal.getEmf().createEntityManager(), usuario);
            perfilUsuario = persistenciaConexionInicial.perfilUsuario(FactoryGlobal.getEmf().createEntityManager(), secPerfil);
            FactoryGlobal.getEmf().close();
            return FactoryGlobal.crearFactoryUsuario(usuario, contraseña, baseDatos);
        } catch (Exception e) {
            System.out.println("Error creando EMF AdministrarLogin.conexionUsuario: " + e);
            return false;
        }
    }

    public boolean validarUsuario(String usuario) {
        try {
            boolean resultado = persistenciaConexionInicial.validarUsuario(FactoryGlobal.getEmf().createEntityManager(), usuario);
            return resultado;
        } catch (Exception e) {
            System.out.println("Error AdministrarLogin.validarUsuario: " + e);
            return false;
        }
    }

    public boolean validarConexionUsuario(String usuario, String contraseña, String baseDatos) {
        try {
            em = persistenciaConexionInicial.validarConexionUsuario(FactoryGlobal.getEmf(), usuario, contraseña, baseDatos);
            if (em != null) {
                if (em.isOpen()) {
                    persistenciaConexionInicial.setearUsuario(em, perfilUsuario.getDescripcion(), perfilUsuario.getPwd());
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error AdministrarLogin.validarConexionUsuario: " + e);
            return false;
        }
    }

    public void cerrarSession() {
        if (em.isOpen()) {
            em.getEntityManagerFactory().close();
        }
    }

    @Override
    public Recordatorios recordatorioAleatorio() {
        if (FactoryGlobal.getEmf() != null && FactoryGlobal.getEmf().isOpen()) {
            return persistenciaRecordatorios.recordatorioRandom(FactoryGlobal.getEmf().createEntityManager());
        } else {
            return null;
        }
    }

    @Override
    public String nombreEmpresaPrincipal() {
        if (FactoryGlobal.getEmf() != null && FactoryGlobal.getEmf().isOpen()) {
            return persistenciaEmpresas.nombreEmpresa(FactoryGlobal.getEmf().createEntityManager());
        } else {
            return null;
        }
    }

    public List<String> recordatoriosInicio() {
        if (FactoryGlobal.getEmf() != null && FactoryGlobal.getEmf().isOpen()) {
            List<String> listaRecordatorios = persistenciaRecordatorios.recordatoriosInicio(FactoryGlobal.getEmf().createEntityManager());
            return listaRecordatorios;
        } else {
            return null;
        }
    }

    @Override
    public List<Recordatorios> consultasInicio() {
        if (FactoryGlobal.getEmf() != null && FactoryGlobal.getEmf().isOpen()) {
            List<Recordatorios> listaConsultas = persistenciaRecordatorios.consultasInicio(FactoryGlobal.getEmf().createEntityManager());
            return listaConsultas;
        } else {
            return null;
        }
    }

    @Override
    public int cambioClave(String usuario, String nuevaClave) {
        if (FactoryGlobal.getEmf() != null && FactoryGlobal.getEmf().isOpen()) {
            return persistenciaConexionInicial.cambiarClave(em, usuario, nuevaClave);
        } else {
            return -1;
        }
    }

    public void guardarDatosConexion(Conexiones conexion) {
        persistenciaConexiones.verificarSID(em, conexion);
    }

    @Override
    public String usuarioBD() {
        return persistenciaActualUsuario.actualAliasBD_EM(em);
    }
}
