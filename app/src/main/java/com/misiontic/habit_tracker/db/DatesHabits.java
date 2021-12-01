package com.misiontic.habit_tracker.db;

import java.util.UUID;

public class DatesHabits {
    interface ColumnsHabits {
        String ID = "id";
        String NAME = "name";
        String DESCRIPTION = "description";
        String CATEGORY = "category";
    }

    interface ColumnsDates {
        String ID = "id";
        String DATE = "date";
        String ID_HABIT = "id_habit";
    }

    public static class Habit implements ColumnsHabits {
        public static String generateIdHabit() {
            return "H-" + UUID.randomUUID().toString();
        }
    }

    public static class Date implements ColumnsDates{
        public static String generateIdDate() {
            return "D-" + UUID.randomUUID().toString();
        }
    }

    private DatesHabits() {
    }

}


