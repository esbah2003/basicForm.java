
package basicForm;
import BasicIO.*;

/** This class ...
 *
 * @author Esbah Majoka
 * @version 5.0 (Feb 18th, 2021)
 * */

public class basicForm  { //
    private final Node [] document; // Array of Nodes
    private BasicForm form = null;  // basicForm
    private char [] words; // char array
    private static final int LINES = 25; // number of lines


    public basicForm  ( ) { // constructor
        document = new Node[LINES];
        words = new char[LINES];
        int button = -1 ;          //button
            buildForm();                // buildForm

            while (true) {
                form.clearAll();
                writeData();           // writing data

                button = form.accept();
                if (button == 0) {                 // Apply Edit button is pressed
                    buttons();
                }
                else { // exit button is selected
                    form.close();
                    break;

                }
            }

        }

        private void buttons(){
            int action;                 // action
            int line = 0;                   // line
            int start = -1;                  // start
            int end = -1;
            String insertLine;// this will add the line
            action = form.readInt("action");
            line = form.readInt("line");
            start = form.readInt("start");
            end = form.readInt("end");
            insertLine = form.readString("text");
            words = insertLine.toCharArray();    // String to char in Line input

            if (action == 0) {                          // insert button is selected
                insertButton(line, start);
            }
            else if (action == 1) {                   // delete button is selected
                deleteButton(line, start, end);
            }
            else {                                    // Replace button in case of else
                replaceButton(line, start, end);
            }

    }
        private void buildForm(){ // Build the form
        String[] RadioLabel = {"Insert","Delete","Replace"};
        form = new BasicForm("Apply Edit","Exit");
        form.addTextField("line","Line",4,15,10);
        form.addTextField("start","Start",4,80,10);
        form.addTextField("end","End",4,150,10);
        form.addTextField("text","Text",30);
        form.addRadioButtons("action","Edit Action",false,RadioLabel);
        form.addTextArea("output","OutPut",20,50);
    }
        private void writeData ( ) { //This method writes the data to the form
        form.writeInt("start", -1);     // start and end have -1
        form.writeInt("end", -1);
        form.writeInt("line", 1);
        for ( int i = 1; i < document.length; i++ ) { // run through node array
            Node p = document[i];
            form.writeInt("output", i);
            form.writeString("output", " :");
            while ( p != null ) {               // not null
                form.writeChar("output", p.item);
                p = p.next;
            }
            form.writeString("output", "\n");
        }
    }

    private void insertButton ( int line, int start ) { // This method insert the data into form
        Node p = document[line];
        Node q = null;

        if ( start < 0 ) { // shift one array down
            for ( int j = document.length; j >line; j-- ) {   // running through array
                document[j - 1] = document[j - 2];
            }

            document[line] = new Node(words[0], null);
            q = document[line];
            for ( int i = 1; i < words.length; i++ ) {      // text
                q.next = new Node(words[i], null);
                q = q.next;
            }
        } else { // If start is specified
            for ( int k = 0; k < start && p != null; k++ ) {      // location finder
                q = p;
                p = p.next;
            }

            for (char c : words) {      // putting text in
                if (q == null) {                              // null
                    document[line] = new Node(c, p);
                    q = document[line];
                } else {
                    q.next = new Node(c, p);
                    q = q.next;
                }
            }
        }
    }

    private void deleteButton( int line, int start, int end ) { // This methods deletes the data from the form
        Node p = document[line];
        Node q = null;

        if ( start > -1 && end < 0 ) { // deleting the char
            for ( int h = 0; h < start - 1 && p != null; h++ ) {    // location finder
                q = p;
                p = p.next;
            }
            if ( p == null ) {                                      // null
                return;
            } else {                                                // delete element
                q.next = p.next;
            }

        } else if ( start > -1 && end > -1 ) { // delete char in told location
            for ( int i = 0; i < start && p != null; i++ ) {
                q = p;
                p = p.next;
            }
            if ( p == null ) {                                              // null
                return;
            } else {                                                        // delete char
                for ( int j = 0; j <= end - start && p != null; j++ ) {     // deleting certain chars
                    if ( q == null ) {                                      // null
                        document[line] = p;
                    } else {
                        q.next = p.next;
                    }
                    p = p.next;
                }
            }
        } else {                                                    // delete row
            for ( int k = line; k < document.length - 1; k++ ) {   // element in the row
                document[k] = document[k + 1];                    // move it one up
            }
        }
    }


    private void replaceButton ( int line, int start, int end ) { //This method replace the data in the form
        Node p = document[line];
        Node q = null;

        for ( int i = 0; i < start & p != null; i++ ) { // This will find location for inserting the data
            q = p;
            p = p.next;
        }
        if ( p == null ) {                              // null list
            return;
        }
        else {                                        //keep searching
            for ( int j = 0; j <= end - start && p != null; j++ ) {     // replace number of chars
                if ( q == null ) {
                    document[line] = p;
                } else {
                    q.next = p.next;
                }
                p = p.next;
            }
        }
        for (char c : words) {          //  replace current text
            if (q == null) {
                document[line] = new Node(c, null);
                q = document[line];
            } else if (p == null) {
                q.next = new Node(c, null);
                q = q.next;
            } else {
                q.next = new Node(c, p);
                q = q.next;
            }
        }
    }
    public static void main(String[] args) { basicForm c = new basicForm(); }
} // main method



