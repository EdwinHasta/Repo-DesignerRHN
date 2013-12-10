package InterfacePersistencia;

import Entidades.Inforeportes;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'Inforeportes' 
 * (Para esta interface las busquedas son operaciones sobre la tabla UsuariosInfoReportes)
 * de la base de datos.
 * @author AndresPineda
 */
public interface PersistenciaInforeportesInterface {
    
    /**
     * Método encargado de insertar un Inforeporte en la base de datos.
     * @param inforeportes Inforeporte que se quiere crear.
     */
    public void crear(Inforeportes inforeportes);
    /**
     * Método encargado de modificar un Inforeporte de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param inforeportes Inforeporte con los cambios que se van a realizar.
     */
    public void editar(Inforeportes inforeportes);
    /**
     * Método encargado de eliminar de la base de datos el Contrato que entra por parámetro.
     * @param inforeportes Inforeporte que se quiere eliminar.
     */
    public void borrar(Inforeportes inforeportes);
    /**
     * Método encargado de buscar todos los Inforeportes existentes en la base de datos.
     * @return Retorna una lista de Inforeportes.
     */
    public List<Inforeportes> buscarInforeportes();
    /**
     * Método encargado de buscar los Inforeportes de Nomina permitidos para el usuario actualmente conectado.
     * @return Retorna una lista de Inforeportes
     */
    public List<Inforeportes> buscarInforeportesUsuarioNomina();
    /**
     * Método encargado de buscar los Inforeportes de Laboral permitidos para el usuario actualmente conectado.
     * @return Retorna una lista de Inforeportes
     */
    public List<Inforeportes> buscarInforeportesUsuarioLaboral();
    /**
     * Método encargado de buscar los Inforeportes de Banco permitidos para el usuario actualmente conectado.
     * @return Retorna una lista de Inforeportes
     */
    public List<Inforeportes> buscarInforeportesUsuarioBanco();
    /**
     * Método encargado de buscar los Inforeportes de Contabilidad permitidos para el usuario actualmente conectado.
     * @return Retorna una lista de Inforeportes
     */
    public List<Inforeportes> buscarInforeportesUsuarioContabilidad();
    /**
     * Método encargado de buscar los Inforeportes de Personal permitidos para el usuario actualmente conectado.
     * @return Retorna una lista de Inforeportes
     */
    public List<Inforeportes> buscarInforeportesUsuarioPersonal();
    /**
     * Método encargado de buscar los Inforeportes de Bienestar permitidos para el usuario actualmente conectado.
     * @return Retorna una lista de Inforeportes
     */
    public List<Inforeportes> buscarInforeportesUsuarioBienestar();
    
    
    
}
