package com.example.myandroidapp.entities;

import androidx.room.Entity;

@Entity (tableName = "status")
public enum Status { InProgress, Completed, Dropped, PlanToTake }
