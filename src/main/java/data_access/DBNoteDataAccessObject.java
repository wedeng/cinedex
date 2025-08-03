package data_access;

import entity.User;
import use_case.note.NoteDataAccessInterface;

/**
 * Data access object for note operations.
 * Placeholder implementation for compilation.
 */
public class DBNoteDataAccessObject implements NoteDataAccessInterface {

    @Override
    public String loadNote(User user) {
        // Placeholder implementation
        return "Sample note for " + user.getName();
    }

    @Override
    public String saveNote(User user, String note) throws use_case.note.DataAccessException {
        // Placeholder implementation
        System.out.println("Saving note for " + user.getName() + ": " + note);
        return note; // Return the saved note content
    }
} 