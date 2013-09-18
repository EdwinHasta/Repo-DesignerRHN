package InterfacePersistencia;

import Entidades.Inforeportes;
import java.util.List;

/**
 *
 * @author AndresPineda
 */
public interface PersistenciaInforeportesInterface {
    
    /**
     * 
     * @param inforeportes 
     */
    public void crear(Inforeportes inforeportes);
    /**
     * 
     * @param inforeportes 
     */
    public void editar(Inforeportes inforeportes);
    /**
     * 
     * @param inforeportes 
     */
    public void borrar(Inforeportes inforeportes);
    /**
     * 
     * @param id
     * @return 
     */
    public Inforeportes buscarInforeporte(Object id);
    /**
     * 
     * @return 
     */
    public List<Inforeportes> buscarInforeportes();
    /**
     * 
     * @return 
     */
    public List<Inforeportes> buscarInforeportesUsuarioNomina();
    /**
     * 
     * @return 
     */
    public List<Inforeportes> buscarInforeportesUsuarioLaboral();
    
    public List<Inforeportes> buscarInforeportesUsuarioBanco();
    
    public List<Inforeportes> buscarInforeportesUsuarioContabilidad();
    
    public List<Inforeportes> buscarInforeportesUsuarioPersonal();
    
    public List<Inforeportes> buscarInforeportesUsuarioBienestar();
    
    
    
}
