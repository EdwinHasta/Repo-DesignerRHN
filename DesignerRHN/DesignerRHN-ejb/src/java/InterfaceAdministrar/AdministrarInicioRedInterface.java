/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Empleados;
import Entidades.Recordatorios;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface AdministrarInicioRedInterface {

    public boolean validacionLogin(String baseDatos, String usuario, String contraseña);

    public boolean conexionInicial(String baseDatos);

    public boolean conexionUsuario(String baseDatos, String usuario, String contraseña);

    public boolean validarUsuario(String usuario);

    public boolean validarConexionUsuario(String usuario, String contraseña, String baseDatos);

    public void cerrarSession();

    public boolean conexionDefault();

    public Recordatorios recordatorioAleatorio();

    public String nombreEmpresaPrincipal();

    public List<String> recordatoriosInicio();

    public List<Recordatorios> consultasInicio();

    public int cambioClave(String usuario, String nuevaClave);
}
