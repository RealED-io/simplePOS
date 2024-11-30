package tesda.tcsdi.simplepos.model.dal;

import tesda.tcsdi.simplepos.model.Model;

import java.sql.ResultSet;

public interface CrudAble {
    Model create();
    Model getById(int id);
    Model update();
    void delete();
}
