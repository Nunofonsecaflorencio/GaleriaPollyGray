package model.dao;

import javax.swing.*;
import java.util.ArrayList;

public interface DAO<T>{
    void create(T obj);
    Object read();
    void update(T obj);
    void delete(T obj);
}
