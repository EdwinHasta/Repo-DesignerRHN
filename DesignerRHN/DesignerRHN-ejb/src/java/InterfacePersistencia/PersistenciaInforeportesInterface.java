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
    public List<Inforeportes> buscarInforeportesUsuario();
    
}
