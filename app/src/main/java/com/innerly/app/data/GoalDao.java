package com.innerly.app.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface GoalDao {
    @Insert
    long insert(Goal goal);

    @Query("SELECT * FROM goals WHERE userId = :userId AND isCompleted = 0")
    List<Goal> getActiveGoals(long userId);

    @Query("SELECT * FROM goals WHERE userId = :userId AND isCompleted = 1")
    List<Goal> getArchivedGoals(long userId);

    @Query("SELECT * FROM goals WHERE id = :goalId")
    Goal getGoalById(long goalId);

    @Update
    void update(Goal goal);
}