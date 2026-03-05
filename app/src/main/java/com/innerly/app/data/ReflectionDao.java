package com.innerly.app.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ReflectionDao {
    @Insert
    void insert(Reflection reflection);

    @Query("SELECT * FROM reflections WHERE goalId = :goalId ORDER BY date DESC")
    List<Reflection> getReflectionsForGoal(long goalId);
}
