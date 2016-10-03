package org.cirdles.topsoil.app.progress.util;

/**
 * An undoable action. Instances of the implementing class can be stored in an
 * UndoManager. <p>
 *
 * The Command interface provides a method to undo a previously executed
 * action. The method should return any affected objects to the same state
 * they were in before the action was executed.
 *
 * This interface provides a method for carrying out the original action.
 * This way, once an action is undone, it can also be redone. If possible, this
 * method can be called upon creation of the Command, and executed in place of
 * additional code.
 *
 * This interface provides a method for getting a short description of an
 * action.
 *
 * @author marottajb
 * @see UndoManager
 * @see org.cirdles.topsoil.app.progress.table.TopsoilTableCellEditCommand
 * @see org.cirdles.topsoil.app.progress.table.ClearCellItemCommand
 * @see org.cirdles.topsoil.app.progress.table.NewRowItemCommand
 * @see org.cirdles.topsoil.app.progress.table.ClearRowItemCommand
 * @see org.cirdles.topsoil.app.progress.table.DeleteRowItemCommand
 * @see org.cirdles.topsoil.app.progress.table.ClearColumnItemCommand
 * @see org.cirdles.topsoil.app.progress.table.DeleteColumnItemCommand
 * @see org.cirdles.topsoil.app.progress.table.TableColumnReorderCommand
 */
public interface Command {

    /**
     * Executes the stored action.
     */
    void execute();

    /**
     * Undoes the stored action.
     */
    void undo();

    /**
     * Returns a short message describing the stored action.
     *
     * @return  String action message
     */
    String getActionName();
}
