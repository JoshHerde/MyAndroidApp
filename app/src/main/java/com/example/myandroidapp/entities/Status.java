package com.example.myandroidapp.entities;


public enum Status {
    InProgress {
        @Override
        public String toString() {
            return "In Progress";
        }
    },

    Completed {
        @Override
        public String toString() {
            return "Completed";
        }
    },

    Dropped {
        @Override
        public String toString() {
            return "Dropped";
        }
    },

    PlanToTake {
        @Override
        public String toString() {
            return "Plan to take";
        }
    }
}
