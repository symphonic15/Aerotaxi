package controllers;

import io.jsondb.JsonDBTemplate;

import java.io.File;
import java.util.List;

public class JsonDB {
    // Path de archivos json
    private String dbFilesLocation = new File("db").getAbsolutePath();
    // Nombre del paquete de modelos (POJOs)
    private String baseScanPackage = "models";

    // Instancia privada al ORM de jsonDB
    private JsonDBTemplate jsonDBInstance() {
        return new JsonDBTemplate(this.dbFilesLocation, this.baseScanPackage);
    }
    // Crea el archivo si no existe
    private void createIfNotExist(Class type) {
        if(!this.jsonDBInstance().collectionExists(type)) {
            this.jsonDBInstance().createCollection(type);
        }
    }

    // Inserta una instancia en el archivo json correspondiente a su tipo
    public void insert(Object instance) {
        this.createIfNotExist(instance.getClass());
        this.jsonDBInstance().insert(instance);
    }

    // Actualiza los valores de una instancia ya cargada
    public void update(Object instance) {
        this.createIfNotExist(instance.getClass());
        this.jsonDBInstance().save(instance, instance.getClass());
    }

    // Si la instancia no existe la inserta, caso contrario, la actualiza
    public void upsert(Object instance) {
        this.createIfNotExist(instance.getClass());
        this.jsonDBInstance().upsert(instance);
    }

    // Elimina una instancia en el archivo json correspondiente a su tipo
    public void remove(Object instance) {
        this.createIfNotExist(instance.getClass());
        this.jsonDBInstance().remove(instance, instance.getClass());
    }

    // Retorna la instancia con el id y tipo definido
    public Object find(String id, Class type) {
        this.createIfNotExist(type);
        return this.jsonDBInstance().findById(id, type);
    }

    // Retorna todas las instancias en el archivo json
    public List<Object> findAll(Class type) {
        this.createIfNotExist(type);
        return this.jsonDBInstance().findAll(type);
    }

    // Retorna una o mas instancias con las condiciones definidas
    public List<Object> findWithQuery(String query, Class type) {
        this.createIfNotExist(type);
        return this.jsonDBInstance().find(query, type);
    }
}
