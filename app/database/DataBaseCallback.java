package database;

import java.sql.ResultSet;

public abstract class DataBaseCallback {
  public void queryCb(ResultSet rs) throws Exception {};
}