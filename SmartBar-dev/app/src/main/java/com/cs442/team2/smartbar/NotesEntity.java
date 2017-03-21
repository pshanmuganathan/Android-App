package com.cs442.team2.smartbar;

/**
 * Created by Sumedhagupta on 11/10/16.
 */

public class NotesEntity {

    int noteId;
    String notes;
    String calendarDate;
    int noteWId;

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCalendarDate() {
        return calendarDate;
    }

    public void setCalendarDate(String calendarDate) {
        this.calendarDate = calendarDate;
    }

    public int getNoteWId() {
        return noteWId;
    }

    public void setNoteWId(int noteWId) {
        this.noteWId = noteWId;
    }
}
