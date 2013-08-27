package InterfacePersistencia;

import Entidades.ObjetosDB;

public interface PersistenciaObjetosDBInterface {
    public ObjetosDB obtenerObjetoTabla(String nombreTabla);
}
