package utils;

import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import org.controlsfx.control.ListSelectionView.ListSelectionAction;

/**
 *
 * @author Duskmourne
 */
public class ListSelect extends ListSelectionAction{
    
    private ChangeListener sourceChangeListener = null;
    
    private ChangeListener targetChangeListener = null;

    /**
     * Get the value of targetChangeListener
     *
     * @return the value of targetChangeListener
     */
    public ChangeListener getTargetChangeListener() {
        return targetChangeListener;
    }

    /**
     * Set the value of targetChangeListener
     *
     * @param targetChangeListener new value of targetChangeListener
     */
    public void setTargetChangeListener(ChangeListener targetChangeListener) {
        this.targetChangeListener = targetChangeListener;
    }


    /**
     * Get the value of sourceChangeListener
     *
     * @return the value of sourceChangeListener
     */
    public ChangeListener getSourceChangeListener() {
        return sourceChangeListener;
    }

    /**
     * Set the value of sourceChangeListener
     *
     * @param sourceChangeListener new value of sourceChangeListener
     */
    public void setSourceChangeListener(ChangeListener sourceChangeListener) {
        this.sourceChangeListener = sourceChangeListener;
    }

    
    public ListSelect(Node graphic) {
        super(graphic);
    }
    
    @Override
    public void initialize(ListView sourceListView, ListView targetListView) {
        sourceListView.getSelectionModel().selectedItemProperty().addListener(sourceChangeListener);
        targetListView.getSelectionModel().selectedItemProperty().addListener(targetChangeListener);
    }
    
    
    
}
