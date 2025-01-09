package Notes;

public class Notes {
    // Defines the 'Notes.Notes' class, representing a currency denomination and its count

    private int Note;
    // Declares a private field to store the denomination value (e.g., 2000, 500)

    private int Count;
    // Declares a private field to store the count of notes for the denomination

    protected Notes(int Note, int Count) {
        // Constructor to initialize a Notes.Notes object with a denomination and count

        this.setNote(Note);
        // Calls the setNote method to assign the provided denomination value to the 'Note' field

        this.setCount(Count);
        // Calls the setCount method to assign the provided count value to the 'Count' field
    }

    public int getNote() {
        // Getter method to retrieve the denomination value

        return Note;
        // Returns the value of the 'Note' field
    }

    public int setNote(int note) {
        // Setter method to update the denomination value

        Note = note;
        // Assigns the provided value to the 'Note' field

        return Note;
        // Returns the updated denomination value
    }

    public int getCount() {
        // Getter method to retrieve the count of notes

        return Count;
        // Returns the value of the 'Count' field
    }

    public int setCount(int count) {
        // Setter method to update the count of notes

        Count = count;
        // Assigns the provided value to the 'Count' field

        return Count;
        // Returns the updated count of notes
    }
}

